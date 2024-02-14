PGDMP     (    1                |            mm    13.1    13.1 ?               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16384    mm    DATABASE     V   CREATE DATABASE mm WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';
    DROP DATABASE mm;
                cwchoiit    false            �            1259    24830 
   department    TABLE     �   CREATE TABLE public.department (
    id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    last_modified_date timestamp(6) without time zone,
    name character varying(255) NOT NULL
);
    DROP TABLE public.department;
       public         heap    cwchoiit    false            �            1259    24828    department_id_seq    SEQUENCE     z   CREATE SEQUENCE public.department_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.department_id_seq;
       public          cwchoiit    false    201                       0    0    department_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.department_id_seq OWNED BY public.department.id;
          public          cwchoiit    false    200            �            1259    24838    employee    TABLE     9  CREATE TABLE public.employee (
    id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    last_modified_date timestamp(6) without time zone,
    employee_number character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    resignation_date date,
    start_date date NOT NULL
);
    DROP TABLE public.employee;
       public         heap    cwchoiit    false            �            1259    24849    employee_history    TABLE     �  CREATE TABLE public.employee_history (
    id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    last_modified_date timestamp(6) without time zone,
    end_date date,
    level character varying(255),
    start_date date,
    worth integer,
    employee_id bigint,
    project_id bigint,
    CONSTRAINT employee_history_level_check CHECK (((level)::text = ANY ((ARRAY['BEGINNER'::character varying, 'HIGH_BEGINNER'::character varying, 'MEDIUM_BEGINNER'::character varying, 'LOW_BEGINNER'::character varying, 'INTERMEDIATE'::character varying, 'HIGH_INTERMEDIATE'::character varying, 'MEDIUM_INTERMEDIATE'::character varying, 'LOW_INTERMEDIATE'::character varying, 'ADVANCED'::character varying, 'HIGH_ADVANCED'::character varying, 'MEDIUM_ADVANCED'::character varying, 'LOW_ADVANCED'::character varying, 'EXPERT'::character varying, 'HIGH_EXPERT'::character varying, 'MEDIUM_EXPERT'::character varying, 'LOW_EXPERT'::character varying])::text[])))
);
 $   DROP TABLE public.employee_history;
       public         heap    cwchoiit    false            �            1259    24847    employee_history_id_seq    SEQUENCE     �   CREATE SEQUENCE public.employee_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.employee_history_id_seq;
       public          cwchoiit    false    205            	           0    0    employee_history_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.employee_history_id_seq OWNED BY public.employee_history.id;
          public          cwchoiit    false    204            �            1259    24858    employee_history_man_month    TABLE     �  CREATE TABLE public.employee_history_man_month (
    id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    last_modified_date timestamp(6) without time zone,
    calculate_level character varying(255),
    calculate_man_month character varying(255),
    calculate_price integer,
    duration_end date,
    duration_start date,
    input_man_month character varying(255),
    input_price integer,
    month integer,
    month_salary integer,
    pl_price integer,
    year integer,
    employee_history_id bigint,
    CONSTRAINT employee_history_man_month_calculate_level_check CHECK (((calculate_level)::text = ANY ((ARRAY['BEGINNER'::character varying, 'HIGH_BEGINNER'::character varying, 'MEDIUM_BEGINNER'::character varying, 'LOW_BEGINNER'::character varying, 'INTERMEDIATE'::character varying, 'HIGH_INTERMEDIATE'::character varying, 'MEDIUM_INTERMEDIATE'::character varying, 'LOW_INTERMEDIATE'::character varying, 'ADVANCED'::character varying, 'HIGH_ADVANCED'::character varying, 'MEDIUM_ADVANCED'::character varying, 'LOW_ADVANCED'::character varying, 'EXPERT'::character varying, 'HIGH_EXPERT'::character varying, 'MEDIUM_EXPERT'::character varying, 'LOW_EXPERT'::character varying])::text[])))
);
 .   DROP TABLE public.employee_history_man_month;
       public         heap    cwchoiit    false            �            1259    24856 !   employee_history_man_month_id_seq    SEQUENCE     �   CREATE SEQUENCE public.employee_history_man_month_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 8   DROP SEQUENCE public.employee_history_man_month_id_seq;
       public          cwchoiit    false    207            
           0    0 !   employee_history_man_month_id_seq    SEQUENCE OWNED BY     g   ALTER SEQUENCE public.employee_history_man_month_id_seq OWNED BY public.employee_history_man_month.id;
          public          cwchoiit    false    206            �            1259    24836    employee_id_seq    SEQUENCE     x   CREATE SEQUENCE public.employee_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.employee_id_seq;
       public          cwchoiit    false    203                       0    0    employee_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.employee_id_seq OWNED BY public.employee.id;
          public          cwchoiit    false    202            �            1259    24870    project    TABLE     �  CREATE TABLE public.project (
    id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    last_modified_date timestamp(6) without time zone,
    contract_number character varying(255) NOT NULL,
    contractor character varying(255),
    end_date date,
    operation_rate character varying(255),
    project_status character varying(255),
    start_date date,
    team_name character varying(255),
    department_id bigint,
    CONSTRAINT project_operation_rate_check CHECK (((operation_rate)::text = ANY ((ARRAY['INCLUDE'::character varying, 'EXCEPT'::character varying])::text[]))),
    CONSTRAINT project_project_status_check CHECK (((project_status)::text = ANY ((ARRAY['YEAR'::character varying, 'SINGLE'::character varying])::text[])))
);
    DROP TABLE public.project;
       public         heap    cwchoiit    false            �            1259    24868    project_id_seq    SEQUENCE     w   CREATE SEQUENCE public.project_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.project_id_seq;
       public          cwchoiit    false    209                       0    0    project_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.project_id_seq OWNED BY public.project.id;
          public          cwchoiit    false    208            �            1259    24883    salary    TABLE     �  CREATE TABLE public.salary (
    id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    last_modified_date timestamp(6) without time zone,
    month character varying(255),
    salary integer,
    year integer,
    employee_id bigint,
    CONSTRAINT salary_month_check CHECK (((month)::text = ANY ((ARRAY['JANUARY'::character varying, 'FEBRUARY'::character varying, 'MARCH'::character varying, 'APRIL'::character varying, 'MAY'::character varying, 'JUNE'::character varying, 'JULY'::character varying, 'AUGUST'::character varying, 'SEPTEMBER'::character varying, 'OCTOBER'::character varying, 'NOVEMBER'::character varying, 'DECEMBER'::character varying])::text[])))
);
    DROP TABLE public.salary;
       public         heap    cwchoiit    false            �            1259    24881    salary_id_seq    SEQUENCE     v   CREATE SEQUENCE public.salary_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.salary_id_seq;
       public          cwchoiit    false    211                       0    0    salary_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.salary_id_seq OWNED BY public.salary.id;
          public          cwchoiit    false    210            �            1259    24892 
   unit_price    TABLE     ~  CREATE TABLE public.unit_price (
    id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    last_modified_date timestamp(6) without time zone,
    level character varying(255),
    worth integer,
    project_id bigint,
    CONSTRAINT unit_price_level_check CHECK (((level)::text = ANY ((ARRAY['BEGINNER'::character varying, 'HIGH_BEGINNER'::character varying, 'MEDIUM_BEGINNER'::character varying, 'LOW_BEGINNER'::character varying, 'INTERMEDIATE'::character varying, 'HIGH_INTERMEDIATE'::character varying, 'MEDIUM_INTERMEDIATE'::character varying, 'LOW_INTERMEDIATE'::character varying, 'ADVANCED'::character varying, 'HIGH_ADVANCED'::character varying, 'MEDIUM_ADVANCED'::character varying, 'LOW_ADVANCED'::character varying, 'EXPERT'::character varying, 'HIGH_EXPERT'::character varying, 'MEDIUM_EXPERT'::character varying, 'LOW_EXPERT'::character varying])::text[])))
);
    DROP TABLE public.unit_price;
       public         heap    cwchoiit    false            �            1259    24890    unit_price_id_seq    SEQUENCE     z   CREATE SEQUENCE public.unit_price_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.unit_price_id_seq;
       public          cwchoiit    false    213                       0    0    unit_price_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.unit_price_id_seq OWNED BY public.unit_price.id;
          public          cwchoiit    false    212            I           2604    24833    department id    DEFAULT     n   ALTER TABLE ONLY public.department ALTER COLUMN id SET DEFAULT nextval('public.department_id_seq'::regclass);
 <   ALTER TABLE public.department ALTER COLUMN id DROP DEFAULT;
       public          cwchoiit    false    200    201    201            J           2604    24841    employee id    DEFAULT     j   ALTER TABLE ONLY public.employee ALTER COLUMN id SET DEFAULT nextval('public.employee_id_seq'::regclass);
 :   ALTER TABLE public.employee ALTER COLUMN id DROP DEFAULT;
       public          cwchoiit    false    202    203    203            K           2604    24852    employee_history id    DEFAULT     z   ALTER TABLE ONLY public.employee_history ALTER COLUMN id SET DEFAULT nextval('public.employee_history_id_seq'::regclass);
 B   ALTER TABLE public.employee_history ALTER COLUMN id DROP DEFAULT;
       public          cwchoiit    false    204    205    205            M           2604    24861    employee_history_man_month id    DEFAULT     �   ALTER TABLE ONLY public.employee_history_man_month ALTER COLUMN id SET DEFAULT nextval('public.employee_history_man_month_id_seq'::regclass);
 L   ALTER TABLE public.employee_history_man_month ALTER COLUMN id DROP DEFAULT;
       public          cwchoiit    false    206    207    207            O           2604    24873 
   project id    DEFAULT     h   ALTER TABLE ONLY public.project ALTER COLUMN id SET DEFAULT nextval('public.project_id_seq'::regclass);
 9   ALTER TABLE public.project ALTER COLUMN id DROP DEFAULT;
       public          cwchoiit    false    209    208    209            R           2604    24886 	   salary id    DEFAULT     f   ALTER TABLE ONLY public.salary ALTER COLUMN id SET DEFAULT nextval('public.salary_id_seq'::regclass);
 8   ALTER TABLE public.salary ALTER COLUMN id DROP DEFAULT;
       public          cwchoiit    false    211    210    211            T           2604    24895    unit_price id    DEFAULT     n   ALTER TABLE ONLY public.unit_price ALTER COLUMN id SET DEFAULT nextval('public.unit_price_id_seq'::regclass);
 <   ALTER TABLE public.unit_price ALTER COLUMN id DROP DEFAULT;
       public          cwchoiit    false    212    213    213            �          0    24830 
   department 
   TABLE DATA           P   COPY public.department (id, created_date, last_modified_date, name) FROM stdin;
    public          cwchoiit    false    201   �Y       �          0    24838    employee 
   TABLE DATA           }   COPY public.employee (id, created_date, last_modified_date, employee_number, name, resignation_date, start_date) FROM stdin;
    public          cwchoiit    false    203   7Z       �          0    24849    employee_history 
   TABLE DATA           �   COPY public.employee_history (id, created_date, last_modified_date, end_date, level, start_date, worth, employee_id, project_id) FROM stdin;
    public          cwchoiit    false    205   �Z       �          0    24858    employee_history_man_month 
   TABLE DATA             COPY public.employee_history_man_month (id, created_date, last_modified_date, calculate_level, calculate_man_month, calculate_price, duration_end, duration_start, input_man_month, input_price, month, month_salary, pl_price, year, employee_history_id) FROM stdin;
    public          cwchoiit    false    207   ~[       �          0    24870    project 
   TABLE DATA           �   COPY public.project (id, created_date, last_modified_date, contract_number, contractor, end_date, operation_rate, project_status, start_date, team_name, department_id) FROM stdin;
    public          cwchoiit    false    209   �]       �          0    24883    salary 
   TABLE DATA           h   COPY public.salary (id, created_date, last_modified_date, month, salary, year, employee_id) FROM stdin;
    public          cwchoiit    false    211   ^                 0    24892 
   unit_price 
   TABLE DATA           d   COPY public.unit_price (id, created_date, last_modified_date, level, worth, project_id) FROM stdin;
    public          cwchoiit    false    213   �^                  0    0    department_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.department_id_seq', 3, true);
          public          cwchoiit    false    200                       0    0    employee_history_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.employee_history_id_seq', 3, true);
          public          cwchoiit    false    204                       0    0 !   employee_history_man_month_id_seq    SEQUENCE SET     P   SELECT pg_catalog.setval('public.employee_history_man_month_id_seq', 18, true);
          public          cwchoiit    false    206                       0    0    employee_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.employee_id_seq', 3, true);
          public          cwchoiit    false    202                       0    0    project_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.project_id_seq', 1, true);
          public          cwchoiit    false    208                       0    0    salary_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.salary_id_seq', 6, true);
          public          cwchoiit    false    210                       0    0    unit_price_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.unit_price_id_seq', 3, true);
          public          cwchoiit    false    212            W           2606    24835    department department_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.department
    ADD CONSTRAINT department_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.department DROP CONSTRAINT department_pkey;
       public            cwchoiit    false    201            a           2606    24867 :   employee_history_man_month employee_history_man_month_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.employee_history_man_month
    ADD CONSTRAINT employee_history_man_month_pkey PRIMARY KEY (id);
 d   ALTER TABLE ONLY public.employee_history_man_month DROP CONSTRAINT employee_history_man_month_pkey;
       public            cwchoiit    false    207            _           2606    24855 &   employee_history employee_history_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.employee_history
    ADD CONSTRAINT employee_history_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.employee_history DROP CONSTRAINT employee_history_pkey;
       public            cwchoiit    false    205            [           2606    24846    employee employee_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.employee DROP CONSTRAINT employee_pkey;
       public            cwchoiit    false    203            c           2606    24880    project project_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.project DROP CONSTRAINT project_pkey;
       public            cwchoiit    false    209            i           2606    24889    salary salary_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.salary
    ADD CONSTRAINT salary_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.salary DROP CONSTRAINT salary_pkey;
       public            cwchoiit    false    211            Y           2606    24900 '   department uk_1t68827l97cwyxo9r1u6t4p7d 
   CONSTRAINT     b   ALTER TABLE ONLY public.department
    ADD CONSTRAINT uk_1t68827l97cwyxo9r1u6t4p7d UNIQUE (name);
 Q   ALTER TABLE ONLY public.department DROP CONSTRAINT uk_1t68827l97cwyxo9r1u6t4p7d;
       public            cwchoiit    false    201            e           2606    24904 $   project uk_b6wvr6lxg2nq8m07ii5nv8002 
   CONSTRAINT     j   ALTER TABLE ONLY public.project
    ADD CONSTRAINT uk_b6wvr6lxg2nq8m07ii5nv8002 UNIQUE (contract_number);
 N   ALTER TABLE ONLY public.project DROP CONSTRAINT uk_b6wvr6lxg2nq8m07ii5nv8002;
       public            cwchoiit    false    209            g           2606    24906 $   project uk_bhlvmt3oslni2jli2ponys97h 
   CONSTRAINT     d   ALTER TABLE ONLY public.project
    ADD CONSTRAINT uk_bhlvmt3oslni2jli2ponys97h UNIQUE (team_name);
 N   ALTER TABLE ONLY public.project DROP CONSTRAINT uk_bhlvmt3oslni2jli2ponys97h;
       public            cwchoiit    false    209            ]           2606    24902 %   employee uk_oosi3suv54trvclvanif87cnt 
   CONSTRAINT     k   ALTER TABLE ONLY public.employee
    ADD CONSTRAINT uk_oosi3suv54trvclvanif87cnt UNIQUE (employee_number);
 O   ALTER TABLE ONLY public.employee DROP CONSTRAINT uk_oosi3suv54trvclvanif87cnt;
       public            cwchoiit    false    203            k           2606    24898    unit_price unit_price_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.unit_price
    ADD CONSTRAINT unit_price_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.unit_price DROP CONSTRAINT unit_price_pkey;
       public            cwchoiit    false    213            m           2606    24912 ,   employee_history fk2s1k12sy33garw085l5o5swcg    FK CONSTRAINT     �   ALTER TABLE ONLY public.employee_history
    ADD CONSTRAINT fk2s1k12sy33garw085l5o5swcg FOREIGN KEY (project_id) REFERENCES public.project(id);
 V   ALTER TABLE ONLY public.employee_history DROP CONSTRAINT fk2s1k12sy33garw085l5o5swcg;
       public          cwchoiit    false    209    2915    205            l           2606    24907 ,   employee_history fk8rv9s5a7ts4dwrmv59p8c8xlw    FK CONSTRAINT     �   ALTER TABLE ONLY public.employee_history
    ADD CONSTRAINT fk8rv9s5a7ts4dwrmv59p8c8xlw FOREIGN KEY (employee_id) REFERENCES public.employee(id);
 V   ALTER TABLE ONLY public.employee_history DROP CONSTRAINT fk8rv9s5a7ts4dwrmv59p8c8xlw;
       public          cwchoiit    false    2907    203    205            q           2606    24932 &   unit_price fkc0y1oa3ehmpymfnn8d68vcd8q    FK CONSTRAINT     �   ALTER TABLE ONLY public.unit_price
    ADD CONSTRAINT fkc0y1oa3ehmpymfnn8d68vcd8q FOREIGN KEY (project_id) REFERENCES public.project(id);
 P   ALTER TABLE ONLY public.unit_price DROP CONSTRAINT fkc0y1oa3ehmpymfnn8d68vcd8q;
       public          cwchoiit    false    209    2915    213            o           2606    24922 #   project fkl7ga8i4ry2amd4mb525tdmjf6    FK CONSTRAINT     �   ALTER TABLE ONLY public.project
    ADD CONSTRAINT fkl7ga8i4ry2amd4mb525tdmjf6 FOREIGN KEY (department_id) REFERENCES public.department(id);
 M   ALTER TABLE ONLY public.project DROP CONSTRAINT fkl7ga8i4ry2amd4mb525tdmjf6;
       public          cwchoiit    false    209    2903    201            p           2606    24927 "   salary fknlnv3jbyvbiu8ci59r3btlk00    FK CONSTRAINT     �   ALTER TABLE ONLY public.salary
    ADD CONSTRAINT fknlnv3jbyvbiu8ci59r3btlk00 FOREIGN KEY (employee_id) REFERENCES public.employee(id);
 L   ALTER TABLE ONLY public.salary DROP CONSTRAINT fknlnv3jbyvbiu8ci59r3btlk00;
       public          cwchoiit    false    203    211    2907            n           2606    24917 6   employee_history_man_month fknsx5bisl6n2ijpmui35kp31yw    FK CONSTRAINT     �   ALTER TABLE ONLY public.employee_history_man_month
    ADD CONSTRAINT fknsx5bisl6n2ijpmui35kp31yw FOREIGN KEY (employee_history_id) REFERENCES public.employee_history(id);
 `   ALTER TABLE ONLY public.employee_history_man_month DROP CONSTRAINT fknsx5bisl6n2ijpmui35kp31yw;
       public          cwchoiit    false    207    205    2911            �   e   x�3�4202�50�50W04�22�22ճ�4�07�'�jÜ�漙����5o�z��0虙X���
qU0|ӽ��*#=cCs�R �@��qqq ��1�      �   �   x�}���0Ek{�,`���l��QP� E"��
$&�#��Y����F �AV�"���7�������7��;��a�年,�/���#�&��颛���_Vk6ڰ�R����s" M$�5���קn�%�����[k?�?      �   �   x�}�=�0�99E/���Ώ���:��� T�����=�}��q$!Ȑ�h�������o�:]�����˴��G� �$c��|ԋ�R�Y��Q苑��w��Ee@7`1��DK<1O�8� ~=W381����~�:�      �     x���Mn�0���)r���(�
L]��A���9FqbKVMgdA���%��+����Y-F�&�� I3!�ax�������y���sP��癃W�-8W���[�[R����d`�c3,Qx4c{7t���7��ĝ�\�lDl�Lڹ����j^l�B�NS^�nU�i<�u�)�I.�����k�U�=7o΀v*�\�2laǕ=�)��~'���V��\�s��<��rc�7�ܸ�:�G���}H.7�zS�M{.����8��\�5��`���M=�"���N:��<�Ǯ��]��^�[�5�׹GM�<�Eq��������uZGT���H�KLJ�я4����k7_#��n��3c�� �B4+g6ݤ ���oZD{7iDƂ6�f�\g��#��I��٬Q�xB;65f��X%���Lu�����6��Evv��nJYxp�R�^T8vn��Uj?�缫Ȗq��]F�5��]tzB��T��^5��������.����y��91�       �   d   x�3�4202�50�50W04�22�26�3�0243�'`h`h����u��o�.)6�54�56���s�	uq�tu���r��=�F\1z\\\ @�$      �   �   x�}�9n�@E�S�"���lG)$��r�sXpaYc�l�*���Dr�Hm�ظ�@���(_�������Z��M��w:Z]��`�^>��r�1)�;D���`�,���Y'U�LO$Ц�#2I��QhOm�1���Nֺ�E�u���K��'�j{�B�������t��t�j�7�>[�         k   x�3�4202�50�50W04�22�26�3�4152�'��������ib ��\FX�[�Z���q�uu�tq�4�f�C��1vgAd]���]]8-���qqq ",0     