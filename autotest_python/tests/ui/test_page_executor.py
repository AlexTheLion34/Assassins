import time

from tests.BaseTestCase import BaseTestCase


class TestExecutorUI(BaseTestCase):
    def test_executor_ui_page(self, request_executor):
        self.sign_in_site('Aragon')
        user_name = self.driver.find_element_by_id('user_name').text
        assert user_name == 'Имя пользователя: Aragon'
        h3 = self.driver.find_elements_by_tag_name('h3')
        assert [el.text for el in h3] == ['Профиль', 'Текущий заказ']

        tr = self.driver.find_element_by_tag_name('tbody>tr')
        tds = tr.find_elements_by_tag_name('td')
        assert [tds[0].text, tds[1].text, tds[2].text] == ['Артефакт', 'Test aim', 'Исполнение']

        btn_primary = tds[3].find_element_by_class_name('btn-primary')

        assert btn_primary.text == 'Просмотреть'

    def test_executor_ui_view_page(self, request_executor):
        self.sign_in_site('Aragon')
        self.driver.get(self.host + 'view-request?id=10000')

        assert self.driver.current_url == self.host + 'view-request?id=10000'
        trs_views = self.driver.find_elements_by_tag_name('tbody>tr')

        check_value = [
            ['Тип:', 'Артефакт'],
            ['Цель:', 'Test aim'],
            ['Цена:', '10'],
            ['Возможная долгота:', '12.0'],
            ['Возможная широта:', '123.0'],
            ['Мечи:', '1'],
            ['Комлект луков и стрел:', '1'],
            ['Ножи:', '1'],
            ['Шиты:', '1'],
            ['Повозка :', 'Не требуется'],
            ['Комлект луков и стрел :', '0'],
            ['Описание:', 'Test description'],
        ]
        assert len(trs_views) == len(check_value)
        for i in range(len(trs_views)):
            tds = trs_views[i].find_elements_by_tag_name('td')
            assert [el.text for el in tds] == check_value[i]

        btn_add = self.driver.find_element_by_class_name('btn-primary')
        assert btn_add.text == 'Добавить отчет'

    def test_executor_ui_add_report(self, request_executor):
        self.sign_in_site('Aragon')
        self.driver.get(self.host + 'add-report')

        header_page = self.driver.find_element_by_class_name('panel-heading')
        assert header_page.text == 'Отчет'

        btn_add = self.driver.find_element_by_class_name('btn-primary')
        assert btn_add.get_attribute('value') == 'Загрузить отчет'
