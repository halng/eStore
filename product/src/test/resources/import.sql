INSERT INTO product_group (id, create_by, create_date, last_update, last_update_by, name, status)
VALUES ('g1', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Group 1', 'ENABLED');
INSERT INTO product_group (id, create_by, create_date, last_update, last_update_by, name, status)
VALUES ('g2', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Group 2', 'DISABLED');
INSERT INTO product_group (id, create_by, create_date, last_update, last_update_by, name, status)
VALUES ('g3', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Group 3', 'ENABLED');

INSERT INTO product (id, create_by, create_date, last_update, last_update_by, blog_post_id, name,
                     price, quantity, is_sales, short_description, slug, thumbnail_url, group_id)
VALUES ('p1', 'SYSTEM', NOW(), NOW(), 'SYSTEM', '', 'Product 1', 22.22, 3, true, '', 'product-1',
        'thumb-1', 'g1');
INSERT INTO product (id, create_by, create_date, last_update, last_update_by, blog_post_id, name,
                     price, quantity, is_sales, short_description, slug, thumbnail_url, group_id)
VALUES ('p2', 'SYSTEM', NOW(), NOW(), 'SYSTEM', '', 'Product 2', 22.22, 3, true, '', 'product-2',
        'thumb-2', 'g2');
INSERT INTO product (id, create_by, create_date, last_update, last_update_by, blog_post_id, name,
                     price, quantity, is_sales, short_description, slug, thumbnail_url, group_id)
VALUES ('p3', 'SYSTEM', NOW(), NOW(), 'SYSTEM', '', 'Product 3', 22.22, 3, true, '', 'product-3',
        'thumb-3', 'g2');


INSERT INTO public.product_attribute (id, create_by, create_date, last_update, last_update_by,
                                      description, name, status)
VALUES ('att1', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Product Attribute 1', 'Att - 1', 'ENABLED');
INSERT INTO public.product_attribute (id, create_by, create_date, last_update, last_update_by,
                                      description, name, status)
VALUES ('att2', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Product Attribute 2', 'Att - 2', 'DISABLED');
INSERT INTO public.product_attribute (id, create_by, create_date, last_update, last_update_by,
                                      description, name, status)
VALUES ('att3', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Product Attribute 3', 'Att - 3', 'ENABLED');
INSERT INTO public.product_attribute (id, create_by, create_date, last_update, last_update_by,
                                      description, name, status)
VALUES ('att4', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Product Attribute 4', 'Att - 4', 'REMOVED');


INSERT INTO public.product_attribute_value (id, create_by, create_date, last_update, last_update_by,
                                            value, product_id, attribute_id)
VALUES ('av1', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Value 1', 'p1', 'att1');
INSERT INTO public.product_attribute_value (id, create_by, create_date, last_update, last_update_by,
                                            value, product_id, attribute_id)
VALUES ('av2', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Value 2', 'p2', 'att4');
INSERT INTO public.product_attribute_value (id, create_by, create_date, last_update, last_update_by,
                                            value, product_id, attribute_id)
VALUES ('av3', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Value 3', 'p2', 'att3');
INSERT INTO public.product_attribute_value (id, create_by, create_date, last_update, last_update_by,
                                            value, product_id, attribute_id)
VALUES ('av4', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Value 4', 'p1', 'att2');
INSERT INTO public.product_attribute_value (id, create_by, create_date, last_update, last_update_by,
                                            value, product_id, attribute_id)
VALUES ('av5', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Value 5', 'p3', 'att2');



INSERT INTO public.product_image (create_by, create_date, last_update, last_update_by, image_url,
                                  product_id)
VALUES ('SYSTEM', NOW(), NOW(), 'SYSTEM', 'img-1', 'p1');
INSERT INTO public.product_image (create_by, create_date, last_update, last_update_by, image_url,
                                  product_id)
VALUES ('SYSTEM', NOW(), NOW(), 'SYSTEM', 'img-2', 'p1');
INSERT INTO public.product_image (create_by, create_date, last_update, last_update_by, image_url,
                                  product_id)
VALUES ('SYSTEM', NOW(), NOW(), 'SYSTEM', 'img-3', 'p1');
INSERT INTO public.product_image (create_by, create_date, last_update, last_update_by, image_url,
                                  product_id)
VALUES ('SYSTEM', NOW(), NOW(), 'SYSTEM', 'img-4', 'p1');
INSERT INTO public.product_image (create_by, create_date, last_update, last_update_by, image_url,
                                  product_id)
VALUES ('SYSTEM', NOW(), NOW(), 'SYSTEM', 'img-5', 'p3');
INSERT INTO public.product_image (create_by, create_date, last_update, last_update_by, image_url,
                                  product_id)
VALUES ('SYSTEM', NOW(), NOW(), 'SYSTEM', 'img-6', 'p2');



INSERT INTO public.product_option (id, create_by, create_date, last_update, last_update_by,
                                   description, display_type, name)
VALUES ('o1', 'SYSTEM', NOW(), NOW(), 'SYSTEM', '', 'number', 'option 1');
INSERT INTO public.product_option (id, create_by, create_date, last_update, last_update_by,
                                   description, display_type, name)
VALUES ('o2', 'SYSTEM', NOW(), NOW(), 'SYSTEM', '', 'color', 'option 2');
INSERT INTO public.product_option (id, create_by, create_date, last_update, last_update_by,
                                   description, display_type, name)
VALUES ('o3', 'SYSTEM', NOW(), NOW(), 'SYSTEM', '', 'number', 'option 3');


INSERT INTO public.product_option_value (id, create_by, create_date, last_update, last_update_by,
                                         value, product_id, product_option_id)
VALUES ('ov1', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Option Value 1', 'p1', 'o1');
INSERT INTO public.product_option_value (id, create_by, create_date, last_update, last_update_by,
                                         value, product_id, product_option_id)
VALUES ('ov2', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Option Value 2', 'p2', 'o1');
INSERT INTO public.product_option_value (id, create_by, create_date, last_update, last_update_by,
                                         value, product_id, product_option_id)
VALUES ('ov3', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Option Value 3', 'p2', 'o3');
INSERT INTO public.product_option_value (id, create_by, create_date, last_update, last_update_by,
                                         value, product_id, product_option_id)
VALUES ('ov4', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Option Value 4', 'p1', 'o3');
INSERT INTO public.product_option_value (id, create_by, create_date, last_update, last_update_by,
                                         value, product_id, product_option_id)
VALUES ('ov5', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'Option Value 5', 'p3', 'o1');

INSERT INTO public.product_seo(id, create_by, create_date, last_update, last_update_by, keyword,
                               metadata, product_id)
VALUES ('s1', 'SYSTEM', NOW(), NOW(), 'SYSTEM', 'key 1', 'metadata 1', 'p1');

