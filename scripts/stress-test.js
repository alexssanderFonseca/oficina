import http from 'k6/http';
import { check, sleep } from 'k6';

// Configurações
const LOGIN_URL = 'http://192.168.49.2:30080/logins';
const TARGET_URL = 'http://192.168.49.2:30080/ordens-servicos';
const DURATION = '3m';

let bearerToken = '';

// Função para obter o bearer token
function getAuthToken() {
  const loginPayload = JSON.stringify({
    username: 'admin',
    password: 'admin'
  });

  const headers = {
    'Content-Type': 'application/json',
  };

  const response = http.post(LOGIN_URL, loginPayload, { headers });

  check(response, {
    'Login bem-sucedido': (r) => r.status === 200,
  });

  // Ajuste conforme a estrutura de resposta da sua API
  const jsonResponse = response.json();
  return jsonResponse.token;
}

// Setup: executado uma única vez antes dos testes
export function setup() {
  console.log('Obtendo bearer token...');
  const token = getAuthToken();
  console.log('Token obtido com sucesso!');
  return token;
}

// Opções de configuração do teste
export const options = {
  vus: 20,
  duration: DURATION
};
// Função principal que roda em loop
export default function (data) {
  const token = data;

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json',
  };

  const response = http.get(TARGET_URL, { headers });

  check(response, {
    'Status é 200': (r) => r.status === 200,
    'Tem conteúdo': (r) => r.body.length > 0,
  });
  sleep(0.5)

}