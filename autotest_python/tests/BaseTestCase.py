import pytest


@pytest.mark.usefixtures("chrome_driver_init")
class BaseTestCase:
    host = 'http://localhost:8088/'

    def sign_in_site(self, username: str = 'Napoleon', password='qwerty'):
        self.driver.get(self.host + 'login')
        username_input = self.driver.find_element_by_name('username')
        username_input.send_keys(username)
        password_input = self.driver.find_element_by_name('password')
        password_input.send_keys(password)
        self.driver.find_element_by_class_name('btn').click()
