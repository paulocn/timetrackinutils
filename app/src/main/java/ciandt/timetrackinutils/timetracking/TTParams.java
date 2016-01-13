package ciandt.timetrackinutils.timetracking;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulocn on 01/10/15.
 */


public class TTParams {
    public List<NameValuePair> mParams;
    public List<NameValuePair> mHeaders;

    public void initialize(){
        if (mParams == null) {
            mParams = new ArrayList<NameValuePair>();
            mParams.add(new BasicNameValuePair("deviceID", "2"));
            mParams.add(new BasicNameValuePair("eventType", "1"));
            mParams.add(new BasicNameValuePair("cracha", ""));
            mParams.add(new BasicNameValuePair("costCenter", ""));
            mParams.add(new BasicNameValuePair("leave", ""));
            mParams.add(new BasicNameValuePair("func", ""));
            mParams.add(new BasicNameValuePair("sessionID", "0"));
            mParams.add(new BasicNameValuePair("selectedEmployee", "0"));
            mParams.add(new BasicNameValuePair("selectedCandidate", "0"));
            mParams.add(new BasicNameValuePair("selectedVacancy", "0"));
            mParams.add(new BasicNameValuePair("dtFmt", "d/m/Y"));
            mParams.add(new BasicNameValuePair("tmFmt", "H:i:s"));
            mParams.add(new BasicNameValuePair("shTmFmt", "H:i"));
            mParams.add(new BasicNameValuePair("dtTmFmt", "d/m/Y H:i:s"));
            mParams.add(new BasicNameValuePair("language", "0"));

            mParams.add(new BasicNameValuePair("cdiDispositivoAcesso", "2"));
            mParams.add(new BasicNameValuePair("cdiDriverDispositivoAcesso", "10"));
            mParams.add(new BasicNameValuePair("cdiTipoIdentificacaoAcesso", "7"));
            mParams.add(new BasicNameValuePair("oplLiberarPETurmaRVirtual", "false"));
            mParams.add(new BasicNameValuePair("cdiTipoUsoDispositivo", "1"));
            mParams.add(new BasicNameValuePair("qtiTempoAcionamento", "0"));
            mParams.add(new BasicNameValuePair("d1sEspecieAreaEvento", "Nenhuma"));
            mParams.add(new BasicNameValuePair("d1sAreaEvento", "Nenhum"));
            mParams.add(new BasicNameValuePair("d1sSubAreaEvento", "Nenhum(a)"));
            mParams.add(new BasicNameValuePair("d1sEvento", "Nenhum"));
            mParams.add(new BasicNameValuePair("oplLiberarFolhaRVirtual", "false"));
            mParams.add(new BasicNameValuePair("oplLiberarCCustoRVirtual", "false"));
            mParams.add(new BasicNameValuePair("qtiHorasFusoHorario", "0"));
            mParams.add(new BasicNameValuePair("cosEnderecoIP", "127.0.0.1"));
            mParams.add(new BasicNameValuePair("nuiPorta", "7069"));
            mParams.add(new BasicNameValuePair("oplValidaSenhaRelogVirtual", "false"));
            mParams.add(new BasicNameValuePair("useUserPwd", "true"));
            mParams.add(new BasicNameValuePair("useCracha", "false"));
            mParams.add(new BasicNameValuePair("dtTimeEvent", ""));
            mParams.add(new BasicNameValuePair("oplLiberarFuncoesRVirtual", "false"));
        }

        if (mHeaders == null){
            mHeaders = new ArrayList<NameValuePair>();
            mHeaders.add(new BasicNameValuePair("Content-type", "application/x-www-form-urlencoded"));
            mHeaders.add(new BasicNameValuePair("Accept", "Accept:*/*"));
            mHeaders.add(new BasicNameValuePair("Cookie", "clockDeviceToken2=nHuH/qaEaN1TzYclwDbze2UcjZeQtjjudvHqcjFufA=="));
            mHeaders.add(new BasicNameValuePair("Origin", "android"));
            mHeaders.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.94 Safari/537.36"));
        }
    }

    public void addParam(String param, String newValue){
        mParams.add(new BasicNameValuePair(param , newValue));
    }

}