package java高级知识.set存储对象问题;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * Created by wxi.wang
 * <p>
 * 2019/7/31 20:13
 * Decription:
 */
@Setter @Getter
public class DirectFlightParam {
    private String depCity;
    private String arrCity;

    public DirectFlightParam(){}

    public DirectFlightParam(String depCity, String arrCity) {
        this.depCity = depCity;
        this.arrCity = arrCity;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + depCity.hashCode();
        result = result * 31 + arrCity.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DirectFlightParam)) {
            return false;
        }
        DirectFlightParam param = (DirectFlightParam)obj;
        return param.arrCity.equals(arrCity) && param.depCity.equals(depCity);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
