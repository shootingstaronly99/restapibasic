
INSERT INTO tag (tag_name)
VALUES ('tagName1');

INSERT INTO tag (tag_name)
VALUES ('tagName2');

INSERT INTO tag (tag_name)
VALUES ('tagName3');

INSERT INTO tag (tag_name)
VALUES ('tagName4');

INSERT INTO tag (tag_name)
VALUES ('tagName5');



INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('agiftCertificate1', 'description1', 1.12, 1, '2022-10-15 11:15:10', '2022-10-05 11:15:10');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('bgiftCertificate2', 'description2', 2.22, 2, '2022-10-10 11:15:10', '2022-10-05 11:15:10');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('cgiftCertificate3', 'description3', 3.33, 3, '2022-10-09 11:15:10', '2022-10-05 11:15:10');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('dgiftCertificate4', 'description4', 4.44, 4, '2022-10-01 11:15:10', '2022-10-05 11:15:10');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('egiftCertificate5', 'description5', 5.55, 2, '2022-10-17 11:15:10', '2022-10-05 11:15:10');


INSERT INTO gift_tags (gift_id, tag_id)
VALUES (1, 4);

INSERT INTO gift_tags (gift_id, tag_id)
VALUES (2, 5);

INSERT INTO gift_tags (gift_id, tag_id)
VALUES (3, 4);

INSERT INTO gift_tags (gift_id, tag_id)
VALUES (4, 3);

INSERT INTO gift_tags (gift_id, tag_id)
VALUES (5, 1);