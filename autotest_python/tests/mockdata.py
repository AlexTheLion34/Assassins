import json
from pathlib import Path

PATH_BASE_MOCK_DATA = Path(__file__).resolve().parent.joinpath('mockdata.json')


def get_mock_data(path: Path =PATH_BASE_MOCK_DATA):
    try:
        with open(path) as f:
            data = json.load(f)
    except Exception as exp:
        data = {}
    finally:
        return data


def get_tuple_data(data: list):
    data_tuple = []
    for el in data:
        data_tuple.append(list(el.values()))
    return data_tuple


if __name__ == '__main__':
    data = get_mock_data()
    print(get_tuple_data(data['valid']['create_request']))
