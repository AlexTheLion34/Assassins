import time

import pytest
from selenium.webdriver.support.select import Select

from tests.BaseTestCase import BaseTestCase
from tests.mockdata import get_mock_data


class TestCustomer(BaseTestCase):
    list_id_element = ['aim', 'price', 'possibleLongitude', 'possibleLatitude', 'description']

    def add_data_form(self, list_id_element, data):
        assert len(list_id_element) == len(data)

        for i in range(len(data)):
            element = self.driver.find_element_by_id(list_id_element[i])
            element.send_keys(data[i])

    def go_to_page_create_request(self):
        self.sign_in_site('Napoleon')
        self.driver.get(self.host + 'add-request')

    def fill_form_create_request(self, data):
        type_select = self.driver.find_element_by_id('type')
        if data['aim'] == 1:
            Select(type_select).select_by_value('Артефакт')
        else:
            Select(type_select).select_by_value('Заказное убийство')
        self.add_data_form(self.list_id_element, list(data.values())[1:])
        self.driver.find_element_by_id('requestInfo').submit()

    # @pytest.mark.parametrize('data', get_mock_data()['valid']['create_request'])
    # def test_create_request_artefact_valid(self, data, request_clear_all_before, request_clear_all_after):
    #     self.go_to_page_create_request()
    #     self.fill_form_create_request(data)
    #     assert self.driver.current_url == self.host + 'profile'
    #
    # @pytest.mark.parametrize('data', get_mock_data()['invalid']['create_request'])
    # def test_create_request_artefact_invalid(self, data, request_clear_all_before, request_clear_all_after):
    #     self.go_to_page_create_request()
    #     self.fill_form_create_request(data)
    #     assert self.driver.current_url == self.host + 'add-request'

    def test_approve_payment(self, request_customer):
        self.sign_in_site('Napoleon')
        self.driver.find_element_by_class_name('btn-success').click()
        assert self.driver.current_url == self.host + 'payment?id=10000'
        self.driver.find_element_by_tag_name('button').click()
        assert self.driver.current_url == self.host + 'profile'

    @pytest.mark.parametrize('rate', ['1', '2', '3', '4', '5'])
    def test_rate_request(self, rate, request_customer_rate):
        self.sign_in_site('Napoleon')
        btns = self.driver.find_elements_by_class_name('btn-primary')
        btns[1].click()
        assert self.driver.current_url == self.host + 'evaluate?id=10000'
        self.driver.find_element_by_id('requestInfo.rating').send_keys(rate)
        self.driver.find_element_by_tag_name('form').submit()
        assert self.driver.current_url == self.host + 'profile'
        tds = self.driver.find_elements_by_tag_name('tbody>tr>td')
        assert tds[2].text == 'Подтверждение оплаты'




