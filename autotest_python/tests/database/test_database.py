import psycopg2
import pytest

from tests.mockdata import get_mock_data


def handle_str_object(data):
    if isinstance(data, str):
        return f"\'{data}\'"
    else:
        return str(data)


def handle_value_query(data):
    for i in range(len(data)):
        data[i] = handle_str_object(data[i])
    return data


def build_query_insert(db_name: str, field_name: list, field_value: list, field_name_return: str):
    query = f'insert into {db_name} ({",".join(field_name)}) values({",".join(handle_value_query(field_value))}) RETURNING {field_name_return};'
    return query


def build_query_remove(db_name: str, field_name: str, field_value):
    query_remove = f"DELETE FROM {db_name} WHERE {field_name} = {handle_str_object(field_value)}"
    return query_remove


def handle_insert_remove_valid(connect_db, db_name: str, field_name: list, field_value: list, field_name_return: str,
                               field_equal):
    query = build_query_insert(db_name, field_name, field_value, field_name_return)
    connect_db.execute(query)
    entry_id = connect_db.fetchone()[0]
    assert entry_id == field_equal
    query_remove = build_query_remove(db_name, field_name_return, entry_id)
    connect_db.execute(query_remove)


def handle_insert_remove_invalid(connect_db, db_name: str, field_name: list, field_value: list, field_name_return: str,
                                 field_equal):
    query = build_query_insert(db_name, field_name, field_value, field_name_return)
    with pytest.raises(Exception):
        connect_db.execute(query)


@pytest.mark.parametrize('data', get_mock_data()['valid']['db']['request'])
def test_create_request_valid(connect_db, data):
    handle_insert_remove_valid(connect_db, 'request', list(data.keys()), list(data.values()), 'id', data['id'])


@pytest.mark.parametrize('data', get_mock_data()['invalid']['db']['request'])
def test_create_request_valid(connect_db, data):
    handle_insert_remove_invalid(connect_db, 'request', list(data.keys()), list(data.values()), 'id', data['id'])


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['request_info'])
def test_create_request_info_valid(connect_db, create_request, data):
    handle_insert_remove_valid(connect_db, 'request_info', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['request_info'])
def test_create_request_info_invalid(connect_db, create_request, data):
    handle_insert_remove_invalid(connect_db, 'request_info', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['request_arsenal'])
def test_create_request_arsenal_valid(connect_db, create_request, data):
    handle_insert_remove_valid(connect_db, 'request_arsenal', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['request_arsenal'])
def test_create_request_arsenal_invalid(connect_db, create_request, data):
    handle_insert_remove_invalid(connect_db, 'request_arsenal', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['report'])
def test_create_report_valid(connect_db, create_request, data):
    handle_insert_remove_valid(connect_db, 'report', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['report'])
def test_create_report_invalid(connect_db, create_request, data):
    handle_insert_remove_invalid(connect_db, 'report', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['request_road_equipment'])
def test_create_request_road_equipment_valid(connect_db, create_request, data):
    handle_insert_remove_valid(connect_db, 'request_road_equipment', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['request_road_equipment'])
def test_create_request_road_equipment_invalid(connect_db, create_request, data):
    handle_insert_remove_invalid(connect_db, 'request_road_equipment', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['request_team'])
def test_create_request_team_valid(connect_db, create_request, data):
    handle_insert_remove_valid(connect_db, 'request_team', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['request_team'])
def test_create_request_team_invalid(connect_db, create_request, data):
    handle_insert_remove_invalid(connect_db, 'request_team', list(data.keys()), list(data.values()), 'request_id', 10000)


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['assassins_user'])
def test_create_assassins_user_valid(connect_db, data):
    handle_insert_remove_valid(connect_db, 'assassins_user', list(data.keys()), list(data.values()), 'id', data['id'])


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['assassins_user'])
def test_create_assassins_user_invalid(connect_db, data):
    handle_insert_remove_invalid(connect_db, 'assassins_user', list(data.keys()), list(data.values()), 'id', data['id'])


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['cabman'])
def test_create_cabman_valid(connect_db, cabman, data):
    handle_insert_remove_valid(connect_db, 'cabman', list(data.keys()), list(data.values()), 'id', 109)


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['cabman'])
def test_create_cabman_invalid(connect_db, cabman, data):
    handle_insert_remove_invalid(connect_db, 'cabman', list(data.keys()), list(data.values()), 'id', 109)


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['customer'])
def test_create_customer_valid(connect_db, customer, data):
    handle_insert_remove_valid(connect_db, 'customer', list(data.keys()), list(data.values()), 'id', 105)


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['customer'])
def test_create_customer_invalid(connect_db, customer, data):
    handle_insert_remove_invalid(connect_db, 'customer', list(data.keys()), list(data.values()), 'id', 109)


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['gunsmith'])
def test_create_gunsmith_valid(connect_db, gunsmith, data):
    handle_insert_remove_valid(connect_db, 'gunsmith', list(data.keys()), list(data.values()), 'id', 108)


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['gunsmith'])
def test_create_gunsmith_invalid(connect_db, gunsmith, data):
    handle_insert_remove_invalid(connect_db, 'gunsmith', list(data.keys()), list(data.values()), 'id', 108)


@pytest.mark.parametrize('data',  get_mock_data()['valid']['db']['master'])
def test_create_master_valid(connect_db, master, data):
    handle_insert_remove_valid(connect_db, 'master', list(data.keys()), list(data.values()), 'id', 107)


@pytest.mark.parametrize('data',  get_mock_data()['invalid']['db']['master'])
def test_create_master_invalid(connect_db, master, data):
    handle_insert_remove_invalid(connect_db, 'master', list(data.keys()), list(data.values()), 'id', 107)







# @pytest.mark.parametrize('data', get_mock_data()['valid']['db']['request'])
# def test_create_request_invalid(connect_db, data):
#     query = build_query_insert('request', list(data.keys()), list(data.values()), 'id')
#     connect_db.execute(query)
#     entry_id = connect_db.fetchone()[0]
#     assert entry_id == data['id']
#     query_remove = build_query_remove('request', 'id', entry_id)
#     connect_db.execute(query_remove)
