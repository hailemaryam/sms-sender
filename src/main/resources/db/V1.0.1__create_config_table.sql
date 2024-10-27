create table SmscConfigItem (
    id varchar(255) not null,
    ip varchar(255),
    language varchar(255),
    okReplyTemplate varchar(255),
    password varchar(255),
    port varchar(255),
    productId varchar(255),
    serviceId varchar(255),
    shortCode varchar(255),
    username varchar(255),
    primary key (id)
) engine=InnoDB;
