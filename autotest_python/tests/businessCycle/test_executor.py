import os
import time
from pathlib import Path

from tests.BaseTestCase import BaseTestCase


class TestExecutor(BaseTestCase):
    path_folder = Path(__file__).resolve().parent.parent

    def test_executor_add_report_bc(self, request_executor):
        path_file = self.path_folder.joinpath('files\\file_example.pdf')

        self.sign_in_site('Aragon')
        self.driver.get(self.host + 'add-report')
        self.driver.find_element_by_id('fileToUpload').send_keys(str(path_file))
        self.driver.find_element_by_id('command').submit()
        assert self.driver.current_url == self.host + 'profile'

    def test_executor_approve_payment(self, request_customer_approve_rate):
        self.sign_in_site('Aragon')
        tds = self.driver.find_elements_by_tag_name('tbody>tr>td')
        assert [el.text for el in tds[:3]] == ['Артефакт', 'Test aim', 'Подтверждение оплаты']
        self.driver.find_element_by_tag_name('form').submit()
        tds = self.driver.find_elements_by_tag_name('tbody>tr>td')
        assert len(tds) == 0
        self.sign_in_site()
        tds = self.driver.find_elements_by_tag_name('tbody>tr>td')
        assert [el.text for el in tds[:3]] == ['Артефакт', 'Test aim', 'Выполнен']
