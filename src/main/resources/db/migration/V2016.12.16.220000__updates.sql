SET @s = (SELECT IF(
    (SELECT COUNT(*)
        FROM INFORMATION_SCHEMA.COLUMNS
        WHERE table_name = 'user'
        AND table_schema = DATABASE()
        AND column_name = 'has_personal_info'
    ) > 0,
    "SELECT 1",
    "ALTER TABLE user ADD COLUMN has_personal_info bit(1) NOT NULL DEFAULT b'0';"
));
PREPARE stmt FROM @s;
EXECUTE stmt;
    
    
SET @s = (SELECT IF(
    (SELECT COUNT(*)
        FROM INFORMATION_SCHEMA.COLUMNS
        WHERE table_name = 'user'
        AND table_schema = DATABASE()
        AND column_name = 'has_payment_info'
    ) > 0,
    "SELECT 1",
    "ALTER TABLE user ADD COLUMN has_payment_info bit(1) NOT NULL DEFAULT b'0';"
));
PREPARE stmt FROM @s;
EXECUTE stmt;
    
SET @s = (SELECT IF(
   (SELECT COUNT(*)
        FROM INFORMATION_SCHEMA.COLUMNS
        WHERE table_name = 'user'
        AND table_schema = DATABASE()
        AND column_name = 'has_idma_verified'
    ) > 0,
    "SELECT 1",
    "ALTER TABLE user ADD COLUMN has_idma_verified bit(1) NOT NULL DEFAULT b'0';"
));
PREPARE stmt FROM @s;
EXECUTE stmt;


SET @s = (SELECT IF(
   (SELECT COUNT(*)
        FROM INFORMATION_SCHEMA.COLUMNS
        WHERE table_name = 'user'
        AND table_schema = DATABASE()
        AND column_name = 'idma_expiration_date'
    ) > 0,
    "SELECT 1",
    "ALTER TABLE user ADD COLUMN idma_expiration_date datetime DEFAULT NULL;"
));
PREPARE stmt FROM @s;
EXECUTE stmt;

SET @s = "SELECT @terole := id from role
		  WHERE description = 'Renter'";
PREPARE stmt FROM @s;
EXECUTE stmt;

SET @s = "SELECT @viewappcap := id from capability
		  WHERE request_id = 'view.my.applications'";
PREPARE stmt FROM @s;
EXECUTE stmt;

insert role2capability (role_id, capability_id) values (@terole, @viewappcap);
