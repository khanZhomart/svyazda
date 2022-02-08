import axios from 'axios'
import { BASE_URL } from '../config'

class PostApi {

    /**
     * Создание поста
     * 
     * @param {Number} post 
     * @param {String} token 
     */
    async create(post, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.post(
            `${BASE_URL}/post-api/`,
            post,
            header
        )
    }

    /**
     * Получение поста по ID
     * 
     * @param {Number} postId 
     * @param {String} token 
     */
    async getById(postId, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(
            `${BASE_URL}/post-api/post?id=${postId}`,
            header
        )
    }
}

export default new PostApi()