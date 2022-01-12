import os
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
