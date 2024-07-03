create table start_information
(
    start_information_id  int2 primary key,
    name varchar(255) not null,
    power float4 not null,
    amount int2 not null,
    cos_f float4 not null,
    k_i float4 not null,
    tg_f float4 not null,
    avg_daily_active_power float4 not null,
    avg_daily_reactive_power float4 not null
);

