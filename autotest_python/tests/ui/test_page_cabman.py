import time

from tests.BaseTestCase import BaseTestCase


class TestCabmanUI(BaseTestCase):
    def test_cabman_ui_page(self, request_cabman):
        self.sign_in_site('Cabman1')
        user_name = self.driver.find_element_by_id('user_name').text
        assert user_name == 'Имя пользователя: Cabman1'
        h3 = self.driver.find_elements_by_tag_name('h3')
        assert [el.text for el in h3] == ['Профиль', 'Заказы для обработки']

        tr = self.driver.find_element_by_tag_name('tbody>tr')
        tds = tr.find_elements_by_tag_name('td')
        assert [tds[0].text, tds[1].text, tds[2].text] == ['Артефакт', 'Test aim', 'Снаряжение поездки']

        btn_primary = tds[3].find_element_by_class_name('btn-primary')
        btn_success = tds[3].find_element_by_class_name('btn-success')

        assert btn_primary.text == 'Просмотр'
        assert btn_success.text == 'Снарядить'

    def test_cabman_ui_page_view_info(self, request_cabman):
        self.sign_in_site('Cabman1')
        self.driver.find_element_by_class_name('btn-primary').click()

        assert self.driver.current_url == self.host + 'view-request?id=10000'
        trs_views = self.driver.find_elements_by_tag_name('tbody>tr')

        check_value = [
            ['Тип:', 'Артефакт'],
            ['Цель:', 'Test aim'],
            ['Цена:', '10'],
            ['Возможная долгота:', '12.0'],
            ['Возможная широта:', '123.0'],
            ['Описание:', 'Test description'],
        ]
        assert len(trs_views) == len(check_value)
        for i in range(len(trs_views)):
            tds = trs_views[i].find_elements_by_tag_name('td')
            assert [el.text for el in tds] == check_value[i]

    def test_cabman_ui_page_add_equipment(self, request_cabman):
        self.sign_in_site('Cabman1')
        self.driver.get(self.host + 'add-road-eq?id=10000')

        select = self.driver.find_element_by_id('carriageRequired')
        options = select.find_elements_by_tag_name('option')
        assert [el.text for el in options] == ['Требуется', 'Не требуется']

        input_cnt_horse = self.driver.find_element_by_id('numOfHorses')
        assert input_cnt_horse.get_attribute("type") == 'number'

        btn_add = self.driver.find_element_by_tag_name('button')
        assert btn_add.text == 'Добавить'
