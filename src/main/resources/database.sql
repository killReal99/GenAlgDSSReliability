create table "public".protection (
    id uuid primary key,
    protection_type varchar(255) not null,
    equipment_type varchar(255) not null,
    false_positive decimal(8,9),
    over_triggering decimal(8,9)),
);

create table "public".ied (
    id uuid primary key,
    name_of_ied varchar(255) not null unique,
    equipment_type varchar(255) not null,
    protection_id uuid references "public".protection (id),
    d2 int,
    d4 int,
    d5 int,
    d8 int,
    d9 int,
    d13 int,
    d14 int,
    d15 int,
    d17 int,
    d18 int,
    d23 int,
    failure_triggering decimal(8,9))
);

create table "public".improsed_measures (
    id uuid primary key,
    d3 int,
    d7 int,
    d11 int,
    d19 int,
    d20 int,
    d21 int,
    d24 int
);

create table "public".organizational_measures (
    id uuid primary key,
    d1 int,
    d6 int,
    d10 int,
    d12 int,
    d16 int,
    d22 int
);

create table "public".substation_measures_per_year (
    id uuid primary key,
    total_price decimal(12,5) not null,
    opex_price decimal(12,5) not null,
    capex_price decimal(12,5) not null,
    architecture_type int not null,
    year_number int not null,
    organizational_measures uuid references "public".organizational_measures (id),
    improsed_measures uuid references "public".improsed_measures (id),
    ied_id uuid references "public".ied (id)
);

create table "public".substation_measures (
    id uuid primary key,
    total_price decimal(12,5) not null,
    opex_price decimal(12,5) not null,
    capex_price decimal(12,5) not null,
    substation_measures_per_year_id uuid references "public".substation_measures_per_year (id)
);