import axios from "axios";

const url = "http://localhost:8443/editoralivros/login";

class AutenticacaoService {
    login(user) {
        const config = {
            withCredentials: true
        };

        console.log("User: " + JSON.stringify(user));
        return axios.post(url + "/auth", user, config)
    }
}

export default new AutenticacaoService();

