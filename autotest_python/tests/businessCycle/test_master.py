import time

from tests.BaseTestCase import BaseTestCase


class TestMaster(BaseTestCase):
    def handle_change_executor(self, btn):
        btn.click()
        assert self.driver.current_url == self.host + 'change-team?requestId=10000&role=EXECUTOR'

        h3 = self.driver.find_element_by_tag_name('h3')
        assert h3.text == 'Ассассины'

        ths = self.driver.find_elements_by_tag_name('thead>tr>th')
        assert [el.text for el in ths] == ['Имя', 'Рейтинг', 'Действия']
        self.driver.get(self.host + 'change-request?id=10000')

    def handle_change_gunsmith(self, btn):
        btn.click()
        assert self.driver.current_url == self.host + 'change-team?requestId=10000&role=GUNSMITH'

        h3 = self.driver.find_element_by_tag_name('h3')
        assert h3.text == 'Оружейники'

        ths = self.driver.find_elements_by_tag_name('thead>tr>th')
        assert [el.text for el in ths] == ['Имя', 'Действия']
        self.driver.get(self.host + 'change-request?id=10000')

    def handle_change_cabman(self, btn):
        btn.click()
        assert self.driver.current_url == self.host + 'change-team?requestId=10000&role=CABMAN'

        h3 = self.driver.find_element_by_tag_name('h3')
        assert h3.text == 'Извозчики'

        ths = self.driver.find_elements_by_tag_name('thead>tr>th')
        assert [el.text for el in ths] == ['Имя', 'Действия']
        self.driver.get(self.host + 'change-request?id=10000')

    def test_master_edit_request(self, request_master):
        self.sign_in_site('Master2')
        tds = self.driver.find_elements_by_tag_name('tbody>tr>td')
        tds[3].find_element_by_class_name('btn-warning').click()

        assert self.driver.current_url == self.host + 'change-request?id=10000'
        btn_danger = self.driver.find_elements_by_class_name('btn-danger')

        assert len(btn_danger) == 3
        assert btn_danger[0].text == 'Изменить'

        self.handle_change_executor(btn_danger[0])

        btn_danger = self.driver.find_elements_by_class_name('btn-danger')
        self.handle_change_gunsmith(btn_danger[1])

        btn_danger = self.driver.find_elements_by_class_name('btn-danger')
        self.handle_change_cabman(btn_danger[2])

    def test_master_approve_request(self, request_master):
        self.sign_in_site('Master2')
        tds = self.driver.find_elements_by_tag_name('tbody>tr>td')

        assert len(tds) == 4
        tds[3].find_element_by_class_name('btn-success').click()
        tds = self.driver.find_elements_by_tag_name('tbody>tr>td')
        assert len(tds) == 0
