from parameterized import parameterized, parameterized_class

import pytest
import time
from tests.BaseTestCase import BaseTestCase
from tests.mockdata import get_mock_data, get_tuple_data


class TestCreateRequest(BaseTestCase):

    def add_data_form(self, list_id_element, data):
        for i in range(len(data)):
            element = self.driver.find_element_by_id(list_id_element[i])
            element.send_keys(data[i])

    @parameterized.expand(get_tuple_data(get_mock_data()['valid']['create_request']))
    def test_create_request_artefact_valid(self, *data):
        list_id_element = ['aim', 'price', 'possibleLongitude', 'possibleLatitude', 'description']

        self.sign_in_site('Napoleon')
        self.driver.get(self.host + 'add-request')

        if data[0] == 1:
            self.driver.find_element_by_id('type1').click()
        else:
            self.driver.find_element_by_id('type2').click()

        self.add_data_form(list_id_element, list(data)[1:])
        self.driver.find_element_by_class_name('btn').click()
        assert self.driver.current_url == self.host + 'profile'

    @parameterized.expand(get_tuple_data(get_mock_data()['invalid']['create_request']))
    def test_create_request_artefact_invalid(self, *data):
        list_id_element = ['aim', 'price', 'possibleLongitude', 'possibleLatitude', 'description']

        self.sign_in_site('Napoleon')
        self.driver.get(self.host + 'add-request')

        if data[0] == 1:
            self.driver.find_element_by_id('type1').click()
        else:
            self.driver.find_element_by_id('type2').click()

        self.add_data_form(list_id_element, list(data)[1:])
        self.driver.find_element_by_class_name('btn').click()
        assert self.driver.current_url == self.host + 'profile'


    # @pytest.mark.usefixtures("create_request_info")
    # def test_create_q(self):
    #     self.sign_in_site('Napoleon')
    #
    #     time.sleep(5)


