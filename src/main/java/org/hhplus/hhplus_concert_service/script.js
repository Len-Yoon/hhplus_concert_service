
import http from 'k6/http';
import { sleep } from 'k6';
//import { htmlReport } from "https://raw.githubuser.content.com/benc-uk/k6-reporter/main/dist/bundle.js";


export const options = {
    vus: 4000,
    duration: '30s'
};
export default function() {
    http.get('http://localhost:8080/concert/concertDate?concertId=1');
    sleep(1);
}
