import axios from "axios"
import {ElMessage} from "element-plus"

const defaultError = () => ElMessage.error('发生了一些错误,请联系网站所有者')
const defaultFailure = (message) => ElMessage.warning(message)

function get(url, success, failure = defaultFailure, error = defaultError) {
    axios.get(url, {
        withCredentials: true
    }).then(({data}) => {
        if (data.success)
            success(data.message, data.status)
        else
            failure(data.message, data.status)
    }).catch(error)
}

function getByParam(url, requestData, success, failure = defaultFailure, error = defaultError) {
    axios.get(url, {
        params: requestData,
        withCredentials: true
    }).then(({data}) => {
        if (data.success) {
            success(data.message, data.status);
        } else {
            failure(data.message, data.status);
        }
    }).catch(error);
}

function post(url, data, success, failure = defaultFailure, error = defaultError) {
    axios.post(url, data, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        withCredentials: true
    }).then(({data}) => {
        if (data.success)
            success(data.message, data.status)
        else
            failure(data.message, data.status)
    }).catch(error)
}

function postByJson(url, data, success, failure = defaultFailure, error = defaultError) {
    axios.post(url, data, {
        headers: {
            'Content-Type': 'application/json'
        },
        withCredentials: true
    }).then(({data}) => {
        if (data.success)
            success(data.message, data.status)
        else
            failure(data.message, data.status)
    }).catch(error)
}

export {get, getByParam, post, postByJson}