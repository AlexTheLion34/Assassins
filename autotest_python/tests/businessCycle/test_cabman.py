import time

from tests.BaseTestCase import BaseTestCase


class TestCabman(BaseTestCase):
    def test_cabman_view_request(self, request_cabman):
        self.sign_in_site('Cabman1')
        self.driver.find_element_by_class_name('btn-primary').click()
        assert self.driver.current_url == self.host + 'view-request?id=10000'

    def test_cabman_add_equipment(self, request_cabman):
        self.sign_in_site('Cabman1')
        self.driver.get(self.host + 'add-road-eq?id=10000')
        self.driver.find_element_by_tag_name('button').click()
        assert self.driver.current_url == self.host + 'profile'
