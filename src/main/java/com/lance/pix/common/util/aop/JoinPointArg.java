package com.lance.pix.common.util.aop;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author lancer1126
 * @Date 2020-12-4
 * @Description JointPoint
 */
@Data
@AllArgsConstructor
public class JoinPointArg {
    private int index;
    private Object value;
}