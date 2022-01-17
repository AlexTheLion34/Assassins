from tests.BaseTestCase import BaseTestCase


class TestUI(BaseTestCase):
    def test_create_request_ui(self):
        self.sign_in_site('Napoleon')
        self.driver.find_element_by_id('add_request').click()
        assert self.driver.current_url == self.host + 'add-request'

        type_elm = self.driver.find_element_by_id('type')
        aim = self.driver.find_element_by_id('aim')
        price = self.driver.find_element_by_id('price')
        possible_longitude = self.driver.find_element_by_id('possibleLongitude')
        possible_latitude = self.driver.find_element_by_id('possibleLatitude')
        description = self.driver.find_element_by_id('description')
        btn_create = self.driver.find_element_by_tag_name('button')

        assert type_elm.get_attribute("type") == 'select-one'
        assert aim.get_attribute("type") == 'text'
        assert description.get_attribute("type") == 'textarea'
        assert price.get_attribute("type") == 'number'
        assert possible_longitude.get_attribute("type") == 'number'
        assert possible_longitude.get_attribute("type") == 'number'
        assert possible_latitude.get_attribute("type") == 'number'

        elm_select = type_elm.find_elements_by_tag_name('option')
        assert elm_select[0].get_attribute("value") == 'Артефакт'
        assert elm_select[1].get_attribute("value") == 'Заказное убийство'
        assert btn_create.text == 'Создать'

        labels = self.driver.find_elements_by_tag_name('label')
        assert len(labels) == 6
        assert [label.text for label in labels] == ['Тип', 'Цель', 'Цена', 'Возможная долгота', 'Возможная широта',
                                                    'Описание']
