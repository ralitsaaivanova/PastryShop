INSERT INTO users (id, email, password, role, username)
VALUES
    (1, 'admin@gmail.com', '9e2008285711740cddc31b2e78e6f28680df3c5b9be6d3fff727b6e01669c81a3f6015f5fb1d0cc357c5d36a3e1071d5', 'ADMIN', 'admin'),
    (2, 'rali@abv.bg', 'a9a9b3b0edee8ffd50faa2ce11690b8429de0b67faf2cc240e258c778a7b3c1b0316da290ab3f2bf28545b95668b1586', 'BUYER', 'rali'),
    (3, 'andrian@abv.bg', '23f1b61d24bbe2b5dc7d35e3284d9d67642720ec166b42de2e5aca257e7e9f75e0a4e68296de9ff2aba83cc5ae91cd03', 'SELLER', 'andrian');


INSERT INTO categories(id,name,user_id)
VALUES
    (1,'Cakes without chocolate',1),
    (2,'Cheesecakes',1),
    (3,'Chocolate cakes',1),
    (4,'Cupcakes',1),
    (5,'Sweets',1);

INSERT INTO currencies(id,conversion_rate,is_Default,name,short_name)
VALUES
    (1,1,true,'leva','lv'),
    (2,0.51,false,'euro','euro');

INSERT INTO measures(id,name,short_name,is_Default)
VALUES
    (1,'gram','g',true),
    (2,'kilogram','kg',false);