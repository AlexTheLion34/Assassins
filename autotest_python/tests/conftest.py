import os
import shutil
from pathlib import Path
from sys import platform

import psycopg2
import pytest
from dotenv import load_dotenv
from psycopg2._psycopg import OperationalError
from psycopg2.extensions import ISOLATION_LEVEL_AUTOCOMMIT
from selenium import webdriver

PATH_ENV = Path(__file__).resolve().parent.parent.joinpath('.env')
load_dotenv(PATH_ENV)


def get_driver(path, options):
    driver = webdriver.Chrome(executable_path=path, chrome_options=options)
    return driver


@pytest.fixture(scope='class')
def chrome_driver_init(request):
    # Launch without options and gui
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--disable-gpu')

    # Launch with gui
    options = None

    if platform == 'darwin':
        path_to_chrome_driver = Path(__file__).resolve().parent.joinpath('chromedriver_folder/chromedriver')
    else:
        path_to_chrome_driver = Path(__file__).resolve().parent.parent.joinpath('chromedriver_folder/chromedriver.exe')
    driver = get_driver(path_to_chrome_driver, options)
    request.cls.driver = driver
    yield
    driver.close()


@pytest.fixture
def connect_db():
    connection = psycopg2.connect(
        database=os.getenv('DB_NAME'),
        user=os.getenv('USER'),
        password=os.getenv('PASSWORD'),
        host=os.getenv('localhost'),
        port=os.getenv('PORT')
    )
    connection.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
    cursor = connection.cursor()
    yield cursor
    if connection:
        cursor.close()
        connection.close()


def check_data_in_db(connect_db, query):
    connect_db.execute(query)
    result = connect_db.fetchall()
    return result is None


@pytest.fixture
def create_request(connect_db):
    query = 'insert into request (id, executor_id, owner_id) values(10000, 6, 1);'
    connect_db.execute(query)
    yield
    query_del = "DELETE FROM request WHERE id = 10000"
    connect_db.execute(query_del)


@pytest.fixture
def create_request_info(create_request, connect_db):
    query = '''
        insert into request_info 
            (aim, description, difficulty, possible_latitude, possible_longitude, price, status, type, request_id) 
        values('Test aim', 'Test description', 0.3, 123, 12, 10, 'EXECUTING', 'Артефакт', 10000);
    '''
    query_select = 'select * from request_info where request_id=10000;'

    if check_data_in_db(connect_db, query_select):
        connect_db.execute(query)
    yield
    query_del = "DELETE FROM request_info WHERE request_id = 10000"
    connect_db.execute(query_del)


@pytest.fixture
def customer(connect_db):
    query = "insert into assassins_user (id,password,role,username) values(105,'$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S','CUSTOMER','Napoleon') RETURNING id;"
    connect_db.execute(query)
    yield
    query_del = "DELETE FROM assassins_user WHERE id = 105"
    connect_db.execute(query_del)


@pytest.fixture
def executor(connect_db):
    query = "insert into assassins_user (id,password,role,username) values(106,'$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S','EXECUTOR','Altair') RETURNING id;"
    connect_db.execute(query)
    yield
    query_del = "DELETE FROM assassins_user WHERE id = 106"
    connect_db.execute(query_del)


@pytest.fixture
def master(connect_db):
    query = "insert into assassins_user (id,password,role,username) values(107,'$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S','MASTER_ASSASSIN','Master1') RETURNING id;"
    connect_db.execute(query)
    yield
    query_del = "DELETE FROM assassins_user WHERE id = 107"
    connect_db.execute(query_del)


@pytest.fixture
def gunsmith(connect_db):
    query = "insert into assassins_user (id,password,role,username) values(108,'$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S','Gunsmith','Gunsmith1') RETURNING id;"
    connect_db.execute(query)
    yield
    query_del = "DELETE FROM assassins_user WHERE id = 108"
    connect_db.execute(query_del)


@pytest.fixture
def cabman(connect_db):
    query = "insert into assassins_user (id,password,role,username) values(109,'$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S','CABMAN','Cabman1') RETURNING id;"
    connect_db.execute(query)
    yield
    query_del = "DELETE FROM assassins_user WHERE id = 109"
    connect_db.execute(query_del)


@pytest.fixture
def request_master(connect_db, request_clear_all_after):
    query = '''
        insert into request (id, executor_id, master_id, owner_id, cabman_id, gunsmith_id) values(10000, 6, 8, 1, 11, 9);
        insert into request_info 
            (aim, description, difficulty, possible_latitude, possible_longitude, price, status, type, request_id) 
        values('Test aim', 'Test description', 0.3, 123, 12, 10, 'MODERATING', 'Артефакт', 10000);
    '''
    connect_db.execute(query)


@pytest.fixture
def request_gunsmith(connect_db, request_clear_all_after):
    query = '''
        insert into request (id, executor_id, master_id, owner_id, cabman_id, gunsmith_id) values(10000, 6, 8, 1, 11, 9);
        insert into request_info 
            (aim, description, difficulty, possible_latitude, possible_longitude, price, status, type, request_id) 
        values('Test aim', 'Test description', 0.3, 123, 12, 10, 'PACKING_1', 'Артефакт', 10000);
    '''
    connect_db.execute(query)


@pytest.fixture
def request_cabman(connect_db, request_clear_all_after):
    query = '''
        insert into request (id, executor_id, master_id, owner_id, cabman_id, gunsmith_id) values(10000, 6, 8, 1, 11, 9);
        insert into request_info 
            (aim, description, difficulty, possible_latitude, possible_longitude, price, status, type, request_id) 
        values('Test aim', 'Test description', 0.3, 123, 12, 10, 'PACKING_2', 'Артефакт', 10000);
    '''
    connect_db.execute(query)


@pytest.fixture
def request_executor(connect_db, request_clear_all_before, request_clear_all_after):
    query = '''
        insert into request (id, executor_id, master_id, owner_id, cabman_id, gunsmith_id) 
            values(10000, 3, 8, 1, 11, 9);
        insert into request_arsenal (num_of_bows, num_of_knives, num_of_shields, num_of_swords, request_id) 
            values(1, 1, 1, 1, 10000); 
        insert into request_road_equipment (carriage_required, num_of_horses, request_id) values(false, 0, 10000); 
        insert into request_info 
            (aim, description, difficulty, possible_latitude, possible_longitude, price, status, type, request_id) 
            values('Test aim', 'Test description', 0.3, 123, 12, 10, 'EXECUTING', 'Артефакт', 10000);
    '''
    connect_db.execute(query)


@pytest.fixture
def request_customer(connect_db, request_clear_all_before, request_clear_all_after):
    query = '''
        insert into request (id, executor_id, master_id, owner_id, cabman_id, gunsmith_id) 
            values(10000, 3, 8, 1, 11, 9);
        insert into request_arsenal (num_of_bows, num_of_knives, num_of_shields, num_of_swords, request_id) 
            values(1, 1, 1, 1, 10000); 
        insert into request_road_equipment (carriage_required, num_of_horses, request_id) values(false, 0, 10000); 
        insert into report (path, request_id) values('storage\file_example.pdf', 10000); 
        insert into request_info 
            (aim, description, difficulty, possible_latitude, possible_longitude, price, status, type, request_id) 
            values('Test aim', 'Test description', 0.3, 123, 12, 10, 'CONFIRMING', 'Артефакт', 10000);
    '''
    connect_db.execute(query)
    shutil.copy(str(Path(__file__).resolve().parent.joinpath('files\\file_example.pdf')), str(Path(__file__).resolve().parent.parent.parent.joinpath('storage')))


@pytest.fixture
def request_customer_rate(connect_db, request_clear_all_before, request_clear_all_after):
    query = '''
        insert into request (id, executor_id, master_id, owner_id, cabman_id, gunsmith_id) 
            values(10000, 3, 8, 1, 11, 9);
        insert into request_arsenal (num_of_bows, num_of_knives, num_of_shields, num_of_swords, request_id) 
            values(1, 1, 1, 1, 10000); 
        insert into request_road_equipment (carriage_required, num_of_horses, request_id) values(false, 0, 10000); 
        insert into report (path, request_id) values('storage\file_example.pdf', 10000); 
        insert into request_info 
            (aim, description, difficulty, possible_latitude, possible_longitude, price, status, type, request_id) 
            values('Test aim', 'Test description', 0.3, 123, 12, 10, 'EVALUATING', 'Артефакт', 10000);
    '''
    connect_db.execute(query)
    shutil.copy(str(Path(__file__).resolve().parent.joinpath('files\\file_example.pdf')), str(Path(__file__).resolve().parent.parent.parent.joinpath('storage')))


@pytest.fixture
def request_customer_approve_rate(connect_db, request_clear_all_before, request_clear_all_after):
    query = '''
        insert into request (id, executor_id, master_id, owner_id, cabman_id, gunsmith_id) 
            values(10000, 3, 8, 1, 11, 9);
        insert into request_arsenal (num_of_bows, num_of_knives, num_of_shields, num_of_swords, request_id) 
            values(1, 1, 1, 1, 10000); 
        insert into request_road_equipment (carriage_required, num_of_horses, request_id) values(false, 0, 10000); 
        insert into report (path, request_id) values('storage\file_example.pdf', 10000); 
        insert into request_info 
            (aim, description, rating, difficulty, possible_latitude, possible_longitude, price, status, type, request_id) 
            values('Test aim', 'Test description', 3, 0.3, 123, 12, 10, 'PAYMENT_CONFIRMING', 'Артефакт', 10000);
    '''
    connect_db.execute(query)
    shutil.copy(str(Path(__file__).resolve().parent.joinpath('files\\file_example.pdf')), str(Path(__file__).resolve().parent.parent.parent.joinpath('storage')))


@pytest.fixture
def request_clear_all_after(connect_db, remove_file_storage_after):
    yield
    query_del = '''
        UPDATE executor SET busy = false;
        DELETE FROM request_info;
        DELETE FROM request_arsenal;
        DELETE FROM request_road_equipment;
        DELETE FROM report;
        DELETE FROM request;
    '''
    connect_db.execute(query_del)


@pytest.fixture
def request_clear_all_before(connect_db):
    query_del = '''
        UPDATE executor SET busy = false;
        DELETE FROM request_info;
        DELETE FROM request_arsenal;
        DELETE FROM request_road_equipment;
        DELETE FROM report;
        DELETE FROM request;
    '''
    connect_db.execute(query_del)


def remove_file():
    dirname = Path(__file__).resolve().parent.parent.parent.joinpath('storage')
    files = os.listdir(dirname)
    for file_name in files:
        path_file = dirname.joinpath(file_name)
        if os.path.exists(path_file):
            os.remove(path_file)


@pytest.fixture
def remove_file_storage_after():
    yield
    remove_file()


@pytest.fixture
def remove_file_storage_before():
    remove_file()
