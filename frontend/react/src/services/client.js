import axios from "axios";


export const getCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VIPE_API_BASE_URL}/api/v1/customers`)
    } catch (e) {
        throw e;
    }
}