INSERT INTO
	users (
	    id,
		full_name,
		email,
		password,
		account_non_expired,
		account_non_locked,
		credentials_non_expired,
		enabled,
		role
	)
VALUES
	(
	    0,
		'Angelo Zero',
		'angelo@zero.com',
		'jake1',
		true,
		true,
		true,
		true,
		'ADMIN'
	);

INSERT INTO
    users (
        id,
        full_name,
        email,
        password,
        account_non_expired,
        account_non_locked,
        credentials_non_expired,
        enabled,
        role
    )
VALUES
    (
        1,
        'Jake Zero',
        'jake@zero.com',
        'jake2',
        true,
        true,
        true,
        true,
        'USER'
    );