import axios from 'axios'
import { BASE_URL } from '../config'

class UserApi {

    /**
     * Авторизация пользователя
     * 
     * @param {String} username 
     * @param {String} password 
     */
    async login(username, password) {
        return await axios.post(
            `${BASE_URL}/user-api/login?username=${username}&password=${password}`
        )
    }

    /**
     * Регистрация пользователя
     * 
     * @param {Object} user 
     */
    async register(user) {
        return await axios.post(
            `${BASE_URL}/user-api/registration`,
            user
        )
    }

    /**
     * Получение профиля по ID пользователя
     * 
     * @param {Number} userId 
     * @param {String} token 
     */
    async getProfileById(userId, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return await axios.get(
            `${BASE_URL}/user-api/profile?id=${userId}`,
            header
        )
    }

    /**
     * Создать запрос для добавления в друзья
     * 
     * @param {Number} targetId 
     * @param {String} token 
     */
    async createFriend(targetId, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return await axios.post(
            `${BASE_URL}/friendship-api/friend-request?targetId=${targetId}`,
            header
        )
    }

    /**
     * Удалить из друзей
     * 
     * @param {*} targetId 
     * @param {*} token 
     */
    async removeFriend(targetId, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return await axios.post(
            `${BASE_URL}/friendship-api/remove?targetId=${targetId}`,
            header
        )
    }

    /**
     * Принять входящий запрос в друзья
     * 
     * @param {*} sourceId 
     * @param {*} token 
     */
    async acceptFriend(sourceId, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return await axios.post(
            `${BASE_URL}/friendship-api/accept?sourceId=${sourceId}`,
            header
        )
    }
}

export default new UserApi()