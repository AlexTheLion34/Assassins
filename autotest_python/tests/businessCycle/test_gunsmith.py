import time

from tests.BaseTestCase import BaseTestCase


class TestGunsmith(BaseTestCase):
    def test_master_view_request(self, request_gunsmith):
        self.sign_in_site('Gunsmith1')
        self.driver.find_element_by_class_name('btn-primary').click()
        assert self.driver.current_url == self.host + 'view-request?id=10000'

    def test_master_approve(self, request_gunsmith):
        self.sign_in_site('Gunsmith1')
        tds = self.driver.find_elements_by_tag_name('tbody>tr>td')
        assert len(tds) == 4

        tds[3].find_element_by_class_name('btn-success').click()
        assert self.driver.current_url == self.host + 'add-arsenal?id=10000'
        self.driver.find_element_by_tag_name('button').click()
