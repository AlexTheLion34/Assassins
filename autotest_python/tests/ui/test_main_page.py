from parameterized import parameterized

import pytest
import time
from tests.BaseTestCase import BaseTestCase


class TestUI(BaseTestCase):
    def test_main_page(self):
        self.driver.get(self.host)
        assert self.driver.title == 'Assassins'

        name_logo = self.driver.find_element_by_class_name('navbar-brand')
        assert name_logo.text == "Assassin's Creed"
        nav_ul = self.driver.find_element_by_tag_name('ul')

        nav_li_a = nav_ul.find_elements_by_tag_name('li>a')
        assert nav_li_a[0].text == 'Войти'

    def test_main_page_after_sign_in_customer(self):
        self.sign_in_site()
        user_name = self.driver.find_element_by_id('user_name').text
        assert user_name == 'Имя пользователя: Napoleon'

        h3 = self.driver.find_elements_by_tag_name('h3')
        assert [el.text for el in h3] == ['Профиль', 'Заказы']

        table_striped = self.driver.find_element_by_class_name('thead-light')
        ths = table_striped.find_elements_by_tag_name('thead>tr>th')

        assert [th.text for th in ths] == ['Тип', 'Цель', 'Статус', 'Действия']

        add_request_btn = self.driver.find_element_by_id('add_request')
        assert add_request_btn.text == 'Новый заказ'

