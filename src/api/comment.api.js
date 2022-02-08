import axios from 'axios'
import { BASE_URL } from '../config'

class CommentApi {

    /**
     * Включение комментария
     * 
     * @param {Number} postId 
     * @param {String} token 
     */
     async enableComment(postId, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return await axios.put(
            `${BASE_URL}/post-api/enable-comment?postId=${postId}`,
            header
        )
    }

    /**
     * Выключение комментария
     * 
     * @param {Number} postId 
     * @param {String} token 
     */
    async disableComment(postId, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return await axios.put(
            `${BASE_URL}/post-api/disable-comment?postId=${postId}`,
            header
        )
    }

    /**
     * Создание комментария
     * 
     * @param {Number} postId
     * @param {String} text
     */
    async create(postId, text, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return await axios.post(
            `${BASE_URL}/comment-api/`,
            {
                postId,
                text
            },
            header
        )
    } 

    /**
     * Редактирование комментария
     * 
     * @param {Number} id
     * @param {String} text
     */
     async edit(id, text, token) {
        const header = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return await axios.put(
            `${BASE_URL}/comment-api/`,
            {
                id,
                text
            },
            header
        )
    } 
}

export default new CommentApi()