package pl.estrix.frontend.web;


import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.data.PageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.estrix.backend.settings.executor.ReadSettingCommandExecutor;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.SettingDto;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.frontend.jsf.FacesViewScope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Getter
@Setter
@Scope(FacesViewScope.NAME)
public abstract class MainController {


    protected static final String EMPTY_IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAMAAABrrFhUAAABAlBMVEVHcEwoLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSooLSpEJM09AAAAVXRSTlMA8+0P9gYJA/n86lokEgwbzNs8LYer4Wbkacnwsc8VGJmNeEI2P6Uz1ee0rk6Wnydvct7St1RgvX5jKopRhEshHth7wJx1uldsosaoMDldw5OBkEhFWGTUygAAEspJREFUeNrsXVlD4swSZQkkAVkVdEARRQUUxV1xxQ033M3//ytXnW/mkqreu9EBrYd5mSRSle5aTp2uBAI/8iM/8iM/8iM/8iP/hjQKXi03Hv62+q96H+Lkqt9T/0Pvr2x+QxPY516vLB1/M/2jk55frMPod9I//OAhyb98H/2nNzySlJPfRP+RvEeW9flvof9czKPKzDfwBK9BjyEnc8Ou/6XjMcUd8kXwy+PK1MgQ6z/rCUiwMbT673tiMhMZTv3LnqjctIcx/V/wxCV0NHT6RyaxmrHrp+fbCbIJDu0hK392cMTb/R3xssshkgV2podJ/8QNUtA6+Pu/yVmSCfJDBBNMT+Fdnu29ILlrYQtY8WHRv32CX+8euOZ4kbAI9ofDERDKv40UvmyLUCVtDgNmOlpHep0lSBeWCIEi0xz88hc7uByt4BnHniA46BlBBZe/j/Sd3cTewpsdaP0b+J3usq5P3mILrAxwaRDH5f8q55ZVR9BlDIJsuyj9u+feNI+dRmZAMYJxpIlzAC65yy8j5Y4xahwaSLz0Arv0Crjk9c1FWC3kCDax5QYwKzzF7xFCnv8hhEswLNqPQxAMDpEK9VG4Rf64iAKq/NYIwWCw8mL8DtdhB/SqpzQYRYkxjp+bgwQYY/irlmK5iCDycvNF9IjCwEAE9gr68ekS20UU0RqoxgY2HEZyGOYM81zEBCoQm7iKrA9E54gA/53Bzu8MdnIbYQEcAQXSL5JUtUqtU6Mdvvt6JqKAEQEkCaVSX1Dfn3Z+e+iN3RKpnMHsh8kI//1/NETQwwhUCoFkuq/L+773J1lPKDKFMfy5APWndohwUyyKk0Lv1xd69xb0S3mQopbS/ARmjN4PSYk4FG/8y8ANAlzhFXod83QNM1/gU54YHaFrIZfqfk1hEKXsXO/8b36Swhbah4/ZZbXE8qRdt4Qt8BWecPSE3tA+/e0Kjtdxh0tKf5wNUdIK59M5ZfYpk9qR33pPXHCb7xQ+h8kQsO4ofxxbwPrkjKj9wOvnnnX3MPx9IVDk9bxWKgBsY6QwtveZ+u/lBVrauHi5gs9hMmRYro2wBiZSn6d/NuSpCNI/cMu6nBneI4sCqXO/5MBS0p8QrV8Zl3MQnwjOiAqfhA+Muyrqu9ukZ9GXwAw3CuMSY/JTMKInz5z+gfA65foVgTzkTCRvMi6k7Kd4Uy6fxZQcWpe8nZZE3mXy5guQUqx/8fl3CLYbBXpA26I+sEHKJ3Jiza8w/ovxz9Y/dNHjebbrNAts0qN0HF+9ILqXw6jUcuY+V/8Vf+hJHlpUzi81SM1q4N0JhJDE+gkTwro9hmuQdo5a2v6yxSywLPOTSqhtVgt/mv6bRFR6fopmgtor5cFXzHKRY4EaH0nrk/6ntAtbVFewRFmfLZdeLvJkGlWl55+iv9tixKcZWqnoUFzBpcWzKgOQzXxKMAT6W2yqzsgSbRHEyIbrrpPLRSELoGxqq+/6h+54N8zXqCcgiL39xKQ6uNee6Ds6AHDL2KgAZPKLWjMutYkI48ql6s9rwjQ01v5y/d/TFKorsFqGX1C12NfaWE3/d+Bkk4YalkxDFDAD27H/Af3f5IXiCi5Mb9IjuNqW/wn931zBapHoBoy76Uu3T8HwVEv/d/++TEBQdvoA1MDyw8xps1Vd/d9dAQZvls0bAFVVRoLhtgH932FEmK31hee3bB4orjhG9A8EImvF/mNXNmwbbujCpCMhQ/q/l209UzIm+4TfRiFEtKD3vHBGoFMnLt3/Topt9O8sbCkjScVmi39FOfp0nNHdcnm8rzTfEZAUO1mNh119fQtaXuZAShhTd4Rd518hokhJA2QdN6oAke2HtsYCgyKzhlIOf+e6MEDMZHgqe1vpKSnf4SZnkE6rwWBodVWecu17xlpgkCQFEKJ1heK76XMlDwN2ZBWGgo6tt4/cvcCASVwUwqcuItdgQvkVAkAMVzYf8oeSUeO/r3qwVi4/pNPpujfx9m+6XL64rJosEGwwoCEvedTQl1IXjFZsld0OjWIVSq+sdg39nRJoT03K5ewGC4peyT7tOFxy2eLpvAmfW3E1KNVr/dgBzScRct1v0Pi2oW8DgOUFZVIZXwxwTLyPxFpajlQUOtdlv9qAzCmTzE7xCMuScvwYVCBWxXb1DkkfByWOqfvFt1bXddVvl5WIde/L9lFrumoLYAPiY2h8qIKjCSvNOJ66uAs6YQHQSaeEK+OMOSe4HfM05Vy9jdYOKvZK/C29Z41t2PH0pbiq7IYBNmCNKN3nKBOvtoKeEamp4ogA1fE2RaFF1wS4HHn0jMm5Yp78osgdAQwXJWj1OO0ZlJpiRQrotBnB3TTi99xTCpuwEvKMiiKpoqJ4yHBMF1Zr8INfKJ0rlw/H32S/XF5KF3nX55RGTQN8rC74kLD//U3I/u17l1fxVBDNspS9mGQGzbTKvIBt7rkVsoDjPJKo+CpLj4c1xn5u/lqk2y6jkBgmwONqagHEkfrTFwztV7mvMdHapNmg3tXeA8Kd+Xn1Pmucem7gXDCrLB1S9kJRPiNYUW2ULKqGwgrF/4WeJJLaaItMr5KnfUDaREb0RtVQOEKOf8FDWepenHigSPpAABzfaqkiq4KhMJEh6r+g4MEBrUSN+JKF95+IF7JKoXCSpP6EIrcgce6JDBdhrUfkTCRyGpVQeEVMYdSJqy+EQcsSLNAUul0moEdD0qFwj3BoKKg15SOxpHCq8m80QfvxVqaWzElTjqIE16097egeG1WQrRLe0DleSzijnpX0mx9/Un/841xdYLgIQZLoXGFGIhDb14SzDrZU5PzY/iYaXm30JjsiOxjNLy/K9AZWSN7sXip3Io1MQXauVg/e6sFKlblRkh2Bs+gwiKJ4JMVzI3/9gB0KjyTPgGZ3l3qPfKWfG1FhbUK8nUX4foHMudpnSjbPCiKRjMQRvkhjiZDlOIuXFJOhiQnL0m9Q5kTeDLWcadNvQgNB0tT10nyk4kX1Q/K7hafl3a6kBjLlHH22E+MxEeirqe3I5jUbLVkj7oQwOCH5wNIAT+c5k+ALMmckZAXzRs9rSaS3gM7wIpJkxYX7AZIBkDnbiBoKbVi75ShpjRBY+hjl4ww18c0oVUPyvn4UF8PeyG46MSkIAp+0+aV9QxiQK0p8nmOV99MoobAmgj/fCXMkvIkmN86cCe5FqQRA4OtfYwIImpch+Zy4TKO4Psr9K8QDvC3cYpYgurdIoa8BAFJSIg7TjiOVxQU2UZX3Z3ICW1GOHnTpEjd9hRsKE8BDF6SdKwlFwxHHT2F2cYEexxpIHCA8Ii3RVRGAFK6cS6XNhRDQUY6LRqyXLaz/o7j+8xZtx0OAlIc85m1xrJwlGQQlhYsshPfA0UkA50gQ5DkxN7zktF/wquuqEWUWOVlqlreCO+IJ4CipF/FnPBcASKfYr9dCaRd1ZhZPkAMrWdSI9IL1l0CARkifeduJUnbwNDMG4PeWU+2KB1PMbKiXwvaKd3BNPAFOrbMBaNAr9Heo7BAHN7lU5wUgY85RGFwEDybRQiiRGlH13vv9obDCaj64MAtOTmgwI16ZOeffLZLF+kuM2Qyn+W04Xyj01xaHHCrSjA41JM0sVv8skGxQp40YvSFxFBt0xBPQZ8/YHISUDlUSE5t8XPaQTdPfeRXvwXWE/O8YjTpog9VX5fRndZeAr6JqUvSXQQCJU48w5pYIUVxzFSw92+gCwDXPAmz1Efa/zEyicY+RABCT2XtmkrujWwNAuWbl3Y9k/WWmtJDGvxETiD+hcI29xCH6uq5rAAdEla4fFCBl8FIz2QgrdILczf8IhTeIpVJgOq2KPkcQbGe79xfHSPrL0ZtxAeXSECR77pXQIgYeaM+oC3wXOHTH1ykj6L8ipX8AZylTUvengPUiDJ+tyBKNSiXWOUliK+7oyR2UAzhV3f+/xyZ4svMyidWi7MQATOooSplwm2m9cRMGmJXA1s6k+9HT2AlIUXpAmANHFM9NGAA8855xaUGBUdzR8yJlZtQ2Qpqf4LWh/1/AqvAxMFplJdR9yDMzRChKklV99mJkSt9fs3Gq8iRx+w2rbZAwoj8IrVQDqA7Pwt2AoMQSOGEZoGnGAA0hA9RV+ViRvBQTAkiMdWfWjAHGRR4aUj/gh9spEkfFiiwDvJoxwKyAEwxqjM+zpwQAaZoANzfTDwMc8iFGS2tGV5a37RhC7iQQIUxlGeMmQo7mV9kxp6ou6gctVuViyAmesjDIjwpEd5p0AhM3cmo+4KEfYXCN87YUx0Vx9lVcKQrkebWmfhTAGK6JSc0YGRRj5IKz5nDixKYRA2yx1pyhMSfTeBN0hG6ElORjRjNTVXykwDZ7g5jcBELQ6iSTHHJkvhZogP/cDRgSzF8MihwXLDN/T9QyoL8fZNlXr1vYksTnnQoC6Ar8rPwmD3DS7ZDeMBIvPZlzVPBVuHVCfi+4ZToT9rMx0gGTcqGSEFbZtWvUwDn6O7pXKRs1ACkWct1A1GV3FZ+19fevqbJqC0yoT44/pcofPrPBrqNGXF0DlBlsjK5ZAwT2MITF/W7PCgfGXzCKivvHggSND7sk+CxenomQ7wOzS2DdpptzMWBccNeBN4NqBN4ATyfu6xnA1+kFnNQr8wawz0R7pdTuF6TJaXGEvFCYEajMT7sMBEr453LmUy+zy3dFmigxCYj4f1wm0A+5cyQdIcr369Bgt+r6+4n348rYrR5Mzt5r0SAPSgirsySOWC2Mfn1gEtewbkUmEOL+8p0qUeiWtZdOAv0SnBHGWBlhhY8mKbqBE58HhGczL/pmgCj+oOtGUhwVeovdEV7RKEaN+F9719+UNhBEN0BIIBBSHEUFjK04gSIICmqLdbBYFRX8/f2/SsWZWuV2LxcwkIS8vxmYW5Lb3be7bz/e8mMDXarpmgEwV8Bb7Ny0K+kDNY7Mxdi0T0bixxufiiV2eoAzrhtlrkG2zRucy+sN+dTCwE0DwAkbwK45iR8LIh/i//9rfG97C+6CHfLhrO8yJRHPeekkK4iNpRRmcpo+lknAPrLSkpMUAhv8E2dHVsd8/LheuusPAJrH0h0ISGmpjLQrZUTrBNWonRfJum8A+ZB1hqQkEKIghPqNnEjTTIWJI7Ljb89Mth6wOjS0KoSMDJ2gzVbduu3b/4uJORhFKCkzCwOAyTLlVSocGCTsytr/cMN9DyId9mysItKsFj+xWlx0OPBg3+L4xrzVqBeht4ZEnOxIT3Fme19KMZt2jXfxMzZ5RS1Tj7avmWszWcD1Jtm7SC3BzICMI1KPH6Yk9tynyRQrd9czRnRS3jDO7g8ozZF4i8+SuQ12IDmhidKjrzVmfbrfT7OJWQtmCqSHbEuYSBi9r1PJqXXZUkVlY7YGQFy8ROUhqEaIdDH5b1urDskZV8DWDKWsMJHwyilOqql2gFwr81h9yEoLxQgLbOATImVrkp/V+8+isYXbYHOdGPEgdgn+c9O5ruYj9lU1mA9YPidGLMIxiYXrEYetbJll7FsKc9t8xz6NKkEPpKkhCcOBnk38Dxorfo/P6/ysxOiLBYj2TP2WivP3BXsP9Saurd2f5+ZDmbUAFRHF6YTvtGnvxM0jgjf5CnMFZgFKZLNBk1+JQptXatS1lljDrDcsQDZpcmXD8oUmHvinLpdJvkRqA3jRAlSbXrRqQ/pUOznrv2uUreHRdYVXJPXE4k/MApvUxdQW2TKljjZOGhXbz9V1AK9a4Jp6pTc+ZWrylSnwzuZzxBtyEt7S+mccP3EXBfC0BTj62TvFqc9/dQOeAmaBMoejPd6f6virQ/AaENHm5xWebPfF1cTHP/0hg/egIIFekpvv/nyYqGPYuACPAql0S3yeRtYOHZ4+X98C7wLpfVTtXNWGVs0LX/yFdhw8DWx2z352S3nsCAgKrPSHafA8kNkCsS3vSumyXiYPX+xrXfAHsIUyddE728TXEZ3HwUfIISlvT+zZ7eLnb/nq/C/ZDtL9KLRUZhfPlZdl8Bm2keQ9Yj/GbuFpYl0B38FC8ljVjv3N4pxHDfyI1J7j0sUJPkrZAX/CxLK9cw7xSUjM/wa/ItrDQhmyiZOQ1mqAfxHHyD+VYHDx/RWJHPgZCtoGja4JxptlExr4HEdiNC5uKbLE5quQCLvZY2OvgYxPDsWeIAAoocR27/2YSfwcr3dkIRDoosyf1HyLbqN4xTRiQUCg40oJX4458QK+S8a3IPRSyqPez108/VvZhSDhgJqNOyUIkL0uBAvbzrjfsglBQ6rs4PzraQge9Lrw+f1G/4hCE5RR7csQUKSElHQ7EFzIAroxDQg0Tmy8gapBwJHp8c4vPUHgodzTfXLJEiwCLKo1ZC8FiwGlgTrEogkLA6zfu6fDImFw6Pvq17TIfugPqimweBi8bVpXv8FiQtmpGXnVOEtBiBAhQoQIESKEF/AXzxwT2z1vNbsAAAAASUVORK5CYII=";


    @Autowired
    private ReadSettingCommandExecutor readSettingCommandExecutor;
    private String basePath;
    private static final String PATH_IDENTIFIER = "%s/%s";

    private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    private FacesContext context = FacesContext.getCurrentInstance();

    protected Integer tablePageInx;

    public static List<ShipmentProductDto> tempShipmentProductDtoList = new ArrayList<ShipmentProductDto>();

    public static List<ProductImageVersionDto> tempProductImageVersionDtoList = new ArrayList<ProductImageVersionDto>();

    @PostConstruct
    public void init() {
        if (getRequest().getParameter("table_page")== null){
            tablePageInx = 0;
        }else{
            tablePageInx = Integer.parseInt(getRequest().getParameter("table_page"));
        }


        SettingDto versionDirectorySettingDto = readSettingCommandExecutor.findByName("versionDirectory");
        if (versionDirectorySettingDto != null){
            basePath = versionDirectorySettingDto.getValue();
        }
    }

    public void onPage(PageEvent e) {
        tablePageInx = e.getPage() + 1;
    }


    public String showImage3(String filePath) throws IOException {
        if (StringUtils.isEmpty(filePath)) return EMPTY_IMAGE;
        File file = new File(String.format(PATH_IDENTIFIER,basePath,filePath));
        if (!file.exists()){
            return EMPTY_IMAGE;
        }

        byte[] fileContent = FileUtils.readFileToByteArray(file);
        return Base64.getEncoder().encodeToString(fileContent);
    }

    protected File isVersionFileExist(String filePath){
        File file = new File(String.format(PATH_IDENTIFIER,basePath,filePath));
        if (!file.exists()){
            return null;
        }
        return file;
    }
}
