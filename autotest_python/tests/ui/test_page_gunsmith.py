import time

from tests.BaseTestCase import BaseTestCase


class TestGunsmithUI(BaseTestCase):
    def test_gunsmith_ui_page(self, request_gunsmith):
        self.sign_in_site('Gunsmith1')
        user_name = self.driver.find_element_by_id('user_name').text
        assert user_name == 'Имя пользователя: Gunsmith1'
        h3 = self.driver.find_elements_by_tag_name('h3')
        assert [el.text for el in h3] == ['Профиль', 'Заказы для обработки']

        tr = self.driver.find_element_by_tag_name('tbody>tr')
        tds = tr.find_elements_by_tag_name('td')
        assert [tds[0].text, tds[1].text, tds[2].text] == ['Артефакт', 'Test aim', 'Подбор оружия']

        btn_primary = tds[3].find_element_by_class_name('btn-primary')
        btn_success = tds[3].find_element_by_class_name('btn-success')

        assert btn_primary.text == 'Просмотр'
        assert btn_success.text == 'Укомплектовать'

    def test_gunsmith_ui_page_view_info(self, request_gunsmith):
        self.sign_in_site('Gunsmith1')
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

    def test_gunsmith_ui_page_approve(self, request_gunsmith):
        self.sign_in_site('Gunsmith1')
        self.driver.get(self.host + 'add-arsenal?id=10000')
        labels = self.driver.find_elements_by_tag_name('label')

        assert [el.text for el in labels] == ['Мечи', 'Ножи', 'Комплект луков со стрелами', 'Щиты']

        btn_add = self.driver.find_element_by_tag_name('button')
        assert btn_add.text == 'Добавить'
