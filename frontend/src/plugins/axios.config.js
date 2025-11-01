import axios from "axios";
import constants from '/src/stores/constants'

const api = axios.create({
    baseURL: 'https://' + constants.baseUrl + '/',
    timeout: 10000
});

export default api;