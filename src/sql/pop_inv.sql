-- Query used to upgrade jbilling database to use historic
-- contact info for invoice
-- This is for PostgreSQL only (very simlar for Oracle)

CREATE OR REPLACE FUNCTION populate_contacts() RETURNS integer AS '
DECLARE
    r_invoice RECORD;
    r_contact RECORD;
    r_contact_field RECORD;
    invoice_limit INTEGER;
    contact_seq INTEGER;
    contact_field_seq INTEGER;
    contact_map_seq INTEGER;
BEGIN

    -- initialize the sequences
    SELECT MAX(id) 
      INTO contact_seq
      FROM CONTACT;

    SELECT MAX(id) 
      INTO contact_field_seq
      FROM CONTACT_FIELD;

    SELECT MAX(id) 
      INTO contact_map_seq
      FROM CONTACT_MAP;

    select min(foreign_id)
      into invoice_limit
      from contact_map 
      where table_id = 39;

    FOR r_invoice IN SELECT * FROM invoice WHERE id < invoice_limit ORDER BY id LOOP

        -- select the users primary contact

        SELECT *
          INTO r_contact
          FROM contact
         WHERE user_id = r_invoice.user_id;

        contact_seq := contact_seq + 1;

        INSERT INTO CONTACT VALUES (
           contact_seq,
           r_contact.organization_name,
           r_contact.street_addres1,
           r_contact.street_addres2,
           r_contact.city,
           r_contact.state_province,
           r_contact.postal_code,
           r_contact.country_code,
           r_contact.last_name,
           r_contact.first_name,
           r_contact.person_initial,
           r_contact.person_title,
           r_contact.phone_country_code,
           r_contact.phone_area_code,
           r_contact.phone_phone_number,
           r_contact.fax_country_code,
           r_contact.fax_area_code,
           r_contact.fax_phone_number,
           r_contact.email,
           r_contact.create_datetime,
           r_contact.deleted,
           r_contact.notification_include);

        FOR r_contact_field IN SELECT * FROM contact_field WHERE contact_id = r_contact.id LOOP
          
            contact_field_seq := contact_field_seq + 1;
            INSERT INTO CONTACT_FIELD VALUES (
                contact_field_seq,
                r_contact_field.type_id,
                contact_seq,
                r_contact_field.content);

        END LOOP;

        contact_map_seq := contact_map_seq + 1;
        INSERT INTO CONTACT_MAP VALUES(
            contact_map_seq,
            contact_seq,
            1,
            39,
            r_invoice.id);

    END LOOP;

    RETURN 1;
END;
' LANGUAGE plpgsql;

