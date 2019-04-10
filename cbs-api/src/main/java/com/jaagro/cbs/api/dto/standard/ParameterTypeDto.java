package com.jaagro.cbs.api.dto.standard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 养殖参数类型
 *
 * @author yj
 * @date 2019/3/15 16:04
 */
@Data
@Accessors
@NoArgsConstructor
@AllArgsConstructor
public class ParameterTypeDto implements Serializable {
    private static final long serialVersionUID = -1899816830275828232L;
    /**
     * 养殖参数模板id
     */
    private Integer standardId;
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 参数类型（10-温度,11-湿度,12-光照强度,13-光照时间,14-氮气,15-二氧化碳,16-通风,17-负压值,20-喂料(次/日),21-喂水(次/日),22-饲喂(克/日),23-体重(克/只/日),24-死淘率(%),25-药品/疫苗,99-其他)
     */
    private Integer paramType;
    /**
     * 单位
     */
    private String unit;
    /**
     * 展示顺序
     */
    private Integer displayOrder;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof ParameterTypeDto) {
            ParameterTypeDto other = (ParameterTypeDto) obj;
            if (equalsInteger(this.standardId, other.standardId) &&
                    equalsStr(this.paramName, other.paramName) &&
                    equalsInteger(this.paramType, other.paramType)
                    ) {
                return true;
            }
        }
        return false;
    }

    private boolean equalsStr(String str1, String str2) {
        if (StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)) {
            return true;
        }
        if (!StringUtils.isEmpty(str1) && str1.equals(str2)) {
            return true;
        }
        return false;
    }

    private boolean equalsInteger(Integer a, Integer b) {
        if (a == null && b == null) {
            return true;
        }
        if (a != null && a.equals(b)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (standardId == null ? 0 : standardId.hashCode());
        result = 31 * result + (paramName == null ? 0 : paramName.hashCode());
        result = 31 * result + (paramType == null ? 0 : paramType.hashCode());
        return result;
    }

}
