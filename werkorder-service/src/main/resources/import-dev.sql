INSERT INTO werkorder (id, klantId, monteurId, status, aangemaaktDoor, aanmaakDatum, omschrijving, commentaar, postcode, huisnr, straat, woonplaats)
VALUES(nextval('werkorder_seq'), '100', '1', 'INACTIEF', 'Susanne', '01-10-2019', 'vervangen elektrameter', '', '1234AB', '27', 'Electronlaan', 'Elektradam');

INSERT INTO werkorder (id, klantId, monteurId, status, aangemaaktDoor, aanmaakDatum, omschrijving, commentaar, postcode, huisnr, straat, woonplaats)
VALUES(nextval('werkorder_seq'), '200', '2', 'INACTIEF', 'Klaas', '02-10-2019', 'vervangen gasmeter', '', '4562XQ', '42', 'Natteweg', 'Plaspolderlaan');

