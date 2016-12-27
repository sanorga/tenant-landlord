SET @s = "SELECT @sauserid := id from user
		  WHERE email_id = 'sa@tenant.com'";
PREPARE stmt FROM @s;
EXECUTE stmt;

SET @s = "SELECT @sarole := id from role
		  WHERE description = 'System Administrator'";
PREPARE stmt FROM @s;
EXECUTE stmt;

SET @s = "SELECT @usercap := id from capability
		  WHERE request_id = 'USER'";
PREPARE stmt FROM @s;
EXECUTE stmt;

insert role2capability (role_id, capability_id) values (@sarole, @usercap);

SET @s = "SELECT @larole := id from role
		  WHERE description = 'Landlord'";
PREPARE stmt FROM @s;
EXECUTE stmt;

SET @s = (SELECT IF(
    (SELECT COUNT(*)
        FROM capability
        WHERE request_id = 'invite.applicant'
    ) > 0,
    "SELECT 1",
   "insert capability (request_id,name,is_available, created_by, created_date, modified_by, modified_date) values ('invite.applicant','invite.applicant','Y',@sauserid,now(), @sauserid, now());"
));
PREPARE stmt FROM @s;
EXECUTE stmt;

SET @s = "SELECT @inviteappcap := id from capability
		  WHERE request_id = 'invite.applicant'";
PREPARE stmt FROM @s;
EXECUTE stmt;

insert role2capability (role_id, capability_id) values (@larole, @inviteappcap);
