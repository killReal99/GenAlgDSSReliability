package org.mpei.nti.calculation.modesCalculation;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ProbabilityUnion {
    private static final int MAX_PARAMS = 25;
    private static final float PRECISION_THRESHOLD = 1e-9f;
    private static final int MAX_CACHE_SIZE = 1024;
    private static final int PARALLEL_THRESHOLD = 16;

    // Потокобезопасный LRU-кэш на основе LinkedHashMap
    private static final Map<CacheKey, Float> cache = Collections.synchronizedMap(
            new LinkedHashMap<CacheKey, Float>(MAX_CACHE_SIZE, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<CacheKey, Float> eldest) {
                    return size() > MAX_CACHE_SIZE;
                }
            }
    );

    // Оптимальное количество потоков
    private static final ForkJoinPool customPool = new ForkJoinPool(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2)
    );

    private static class CacheKey {
        private final float[] probs;
        private final int hash;

        public CacheKey(float[] probs) {
            this.probs = Arrays.copyOf(probs, probs.length);
            this.hash = Arrays.hashCode(probs);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CacheKey)) return false;
            CacheKey other = (CacheKey) o;
            return Arrays.equals(probs, other.probs);
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }

    public static float calculateProbability(float... probs) {
        // Проверка входных данных
        if (probs == null || probs.length == 0) return 0.0f;
        if (probs.length > MAX_PARAMS) {
            throw new IllegalArgumentException("Max " + MAX_PARAMS + " parameters supported");
        }

        // Проверка кэша
        CacheKey key = new CacheKey(probs);
        Float cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        // Вычисление результата
        float result = computeUnion(probs);

        // Кэширование результата
        synchronized (cache) {
            cache.put(key, result);
        }
        return result;
    }

    private static float computeUnion(float[] probs) {
        final int n = probs.length;

        // Быстрые проверки тривиальных случаев
        if (n == 1) return clamp(probs[0]);
        for (float p : probs) {
            if (p >= 1.0f - PRECISION_THRESHOLD) return 1.0f;
            if (p <= PRECISION_THRESHOLD) return 0.0f;
        }

        // Выбор оптимального алгоритма
        if (n <= 7) return optimizedSmallUnion(probs);
        if (n <= 15) return optimizedMediumUnion(probs);
        return parallelUnion(probs);
    }

    private static float optimizedSmallUnion(float[] probs) {
        float union = 0.0f;
        final int n = probs.length;

        for (int mask = 1; mask < (1 << n); mask++) {
            float product = computeProduct(probs, mask);
            if (product > PRECISION_THRESHOLD) {
                union += ((Integer.bitCount(mask) & 1) != 0) ? product : -product;
            }
        }

        return clamp(union);
    }

    private static float optimizedMediumUnion(float[] probs) {
        float union = 0.0f;
        final int n = probs.length;
        final int[] bitCounts = new int[1 << n];

        // Предвычисление битовых масок
        for (int mask = 1; mask < (1 << n); mask++) {
            bitCounts[mask] = Integer.bitCount(mask);
        }

        for (int mask = 1; mask < (1 << n); mask++) {
            float product = computeProduct(probs, mask);
            if (product > PRECISION_THRESHOLD) {
                union += ((bitCounts[mask] & 1) != 0) ? product : -product;
            }
        }

        return clamp(union);
    }

    private static float parallelUnion(float[] probs) {
        final int n = probs.length;

        return customPool.submit(() ->
                IntStream.range(1, 1 << n)
                        .parallel()
                        .mapToObj(mask -> {
                            float product = computeProduct(probs, mask);
                            if (product <= PRECISION_THRESHOLD) return 0.0f;
                            return ((Integer.bitCount(mask) & 1) != 0) ? product : -product;
                        })
                        .reduce(0.0f, Float::sum)
        ).join();
    }

    private static float computeProduct(float[] probs, int mask) {
        float product = 1.0f;
        int remaining = mask;

        while (remaining != 0 && product > PRECISION_THRESHOLD) {
            int shift = Integer.numberOfTrailingZeros(remaining);
            product *= probs[shift];
            remaining ^= (1 << shift);
        }

        return product;
    }

    private static float clamp(float value) {
        if (value <= PRECISION_THRESHOLD) return 0.0f;
        if (value >= 1.0f - PRECISION_THRESHOLD) return 1.0f;
        return value;
    }

    public static void clearCache() {
        synchronized (cache) {
            cache.clear();
        }
    }

    public static int getCacheSize() {
        synchronized (cache) {
            return cache.size();
        }
    }
}