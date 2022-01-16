import pytest

from tests.BaseTestCase import BaseTestCase


class TestAuthUI(BaseTestCase):
    @staticmethod
    def compare_requirement_attribute(elm, attribute_name: list, attribute_value: list):
        for i in range(len(attribute_name)):
            assert elm.get_attribute(attribute_name[i]) == attribute_value[i]

    def test_auth_ui(self):
        self.driver.get(self.host + 'login')
        h4 = self.driver.find_element_by_tag_name('h4')
        assert h4.text == 'Вход'

        username_input = self.driver.find_element_by_name('username')
        TestAuthUI.compare_requirement_attribute(username_input, ['name', 'placeholder'],
                                                 ['username', 'Имя пользователя'])

        password_input = self.driver.find_element_by_name('password')
        TestAuthUI.compare_requirement_attribute(password_input, ['name', 'placeholder'], ['password', 'Пароль'])

        btn_submit = self.driver.find_element_by_class_name('btn')
        assert btn_submit.get_attribute('value') == 'Войти'

    @pytest.mark.parametrize('username', [
        # Customer
        'Napoleon',

        # Masters Assassins
        'Master1',
        'Master2',

        # Gunsmith
        'Gunsmith1',
        'Gunsmith2',

        # Cabmans
        'Cabman1',
        'Cabman2',

        # Executor
        'Altair',
        'Aragon',
        'Gerald',
        'Legolas',
        'Gimli',
    ])
    def test_auth_in_system_different_user(self, username):
        self.sign_in_site(username)

        assert self.driver.current_url == self.host + 'profile'

        user_name = self.driver.find_element_by_id('user_name').text
        assert user_name == f'Имя пользователя: {username}'

    @pytest.mark.parametrize('username,password', [
        # Customer
        ['Napoleon', 'qwe'],
        ['Nap', 'qwerty'],
        ['', 'qwerty'],
        ['Napoleon', ' '],
        ['', ''],
    ])
    def test_auth_in_system_different_user_invalid(self, username, password):
        self.sign_in_site(username, password)
        assert self.driver.current_url == self.host + 'login?error'
