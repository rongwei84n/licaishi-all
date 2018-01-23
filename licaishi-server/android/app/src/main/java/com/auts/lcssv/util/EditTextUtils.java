package com.auts.lcssv.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * 编辑框工具类
 * Created by xiaolei.yang on 2017/8/2.
 */

public class EditTextUtils {

    /**
     * 防止编辑框复制粘贴
     */
    public void nothing() {
//        不要使用该方法
//        使用自定义编辑框com.phicomm.phihome.views.NullMenuEditText
    }

    /**
     * 当编辑框需要限制长度，且一个中文字符当作两个英文字符处理时使用
     *
     * @param maxLen   编辑框可输入的最大字符
     * @param editText 编辑框
     */
    public static void setInputLength(final int maxLen, EditText editText) {
        if (editText == null) {
            return;
        }
        if (maxLen == 0) {
            editText.setEnabled(false);
            return;
        }
        if (editText.getFilters() == null || editText.getFilters().length == 0) {
            editText.setFilters(new InputFilter[]{
                    getLengthInputFilter(maxLen)
            });
        } else {
            InputFilter[] oldFilters = editText.getFilters();
            InputFilter[] newFilters = new InputFilter[oldFilters.length + 1];
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
            newFilters[oldFilters.length] = getLengthInputFilter(maxLen);
            editText.setFilters(newFilters);
        }

    }

    /**
     * 当编辑框需要限制长度，且一个中文字符当作两个英文字符处理时使用,并检查是否包含特殊字符
     *
     * @param maxLen   编辑框可输入的最大字符
     * @param editText 编辑框
     */
    public static void setInputLengthAndCheckAscll(final int maxLen, EditText editText) {
        if (editText == null) {
            return;
        }
        if (maxLen == 0) {
            editText.setEnabled(false);
            return;
        }
        if (editText.getFilters() == null || editText.getFilters().length == 0) {
            editText.setFilters(new InputFilter[]{
                    getLengthAndAscllInputFilter(maxLen)
            });
        } else {
            InputFilter[] oldFilters = editText.getFilters();
            InputFilter[] newFilters = new InputFilter[oldFilters.length + 1];
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
            newFilters[oldFilters.length] = getLengthInputFilter(maxLen);
            editText.setFilters(newFilters);

        }

    }

    /**
     * 一个中文字符当作两个英文字符处理时使用，且限制最大长度的InputFilter
     *
     * @param maxLen 最大长度
     * @return
     */
    public static InputFilter getLengthInputFilter(final int maxLen) {

        return new InputFilter() {

            /**
             *
             * @param src 当前输入的文字
             * @param start 输入字符的开始位置
             * @param end   输入字符的结束位置
             * @param dest  当前已经显示的文字
             * @param dStart    已经显示文字的开始位置
             * @param dEnd  已经显示文字的结束位置
             * @return
             */
            @Override
            public CharSequence filter(CharSequence src, int start, int end, Spanned dest, int dStart, int dEnd) {
                int dIndex = 0;
                int count = 0;
                while (count <= maxLen && dIndex < dest.length()) {
                    char c = dest.charAt(dIndex++);
                    if (c < 128) {
                        count = count + 1;
                    } else {
                        count = count + 2;
                    }
                }

                if (count > maxLen) {
                    return dest.subSequence(0, dIndex - 1);
                }
                int sIndex = 0;
                while (count <= maxLen && sIndex < src.length()) {
                    char c = src.charAt(sIndex++);
                    if (c < 128) {
                        count = count + 1;
                    } else {
                        count = count + 2;
                    }
                }

                if (count > maxLen) {
                    sIndex--;
                }

                return src.subSequence(0, sIndex);
            }
        };
    }

    /**
     * 一个中文字符当作两个英文字符处理时使用，且限制最大长度的InputFilter,增加了对ASCll小于33的字符的过滤
     *
     * @param maxLen 最大长度
     * @return
     */
    public static InputFilter getLengthAndAscllInputFilter(final int maxLen) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence src, int start, int end, Spanned dest, int dStart, int dEnd) {
                int dIndex = 0;
                int count = 0;
                int sIndex = 0;
                while (count <= maxLen && sIndex < src.length()) {  //如果有ascll码小于33的特殊字符则忽略
                    char c = src.charAt(sIndex++);
                    if (c < 33) {
                        Log.d("EditTextUtils", "src return :" + c);
                        return src.subSequence(0, sIndex - 1);
                    }
                }
                Log.d("EditTextUtils", src.toString());
                while (count <= maxLen && dIndex < dest.length()) {
                    char c = dest.charAt(dIndex++);
                    if (c < 128) {
                        count = count + 1;
                    } else {
                        count = count + 2;
                    }
                }

                if (count > maxLen) {
                    return dest.subSequence(0, dIndex - 1);
                }
                sIndex = 0;
                while (count <= maxLen && sIndex < src.length()) {
                    char c = src.charAt(sIndex++);
                    if (c < 128) {
                        count = count + 1;
                    } else {
                        count = count + 2;
                    }
                }

                if (count > maxLen) {
                    sIndex--;
                }

                return src.subSequence(0, sIndex);
            }
        };
    }

    /**
     * 获取filter。限制长度（单位是字节），达到某条件时增加显示掩码
     *
     * @param mask 超过某个长度显示掩码
     * @Author weiming.zeng
     */
    public static InputFilter getMaskLenthFilter(final int maxLen, final String mask) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                int count = 0;
                int index = 0;
                while (count <= maxLen && index < source.length()) {
                    char c = source.charAt(index++);
                    if (c < 128) {
                        count += 1; //字符
                    } else {
                        count += 2; //汉字
                    }
                }
                if (count > maxLen) { //超过限制字数
                    return source.subSequence(0, index - 1) + mask;
                }
                return source.subSequence(0, index);
            }
        };
    }

    /**
     * 处理中文、空格的过滤
     */
    private static InputFilter getBlankChineseInputFilter(final boolean allowBlank, final boolean allowChinese) {

        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
                if (!allowBlank && " ".equals(source)) {
                    return "";
                }
                if (!allowChinese && checkNameChese(source.toString())) {
                    return "";
                }
                return null;
            }
        };
    }


    /**
     * @param editText     编辑框对象
     * @param allowBlank   是否允许空格
     * @param allowChinese 是否允许中文
     */
    public static void setBlankChineseInputFilter(EditText editText, final boolean allowBlank, final boolean allowChinese) {
        if (editText == null) {
            return;
        }

        if (editText.getFilters() == null || editText.getFilters().length == 0) {
            editText.setFilters(new InputFilter[]{
                    getBlankChineseInputFilter(allowBlank, allowChinese)
            });
        } else {
            InputFilter[] oldFilters = editText.getFilters();
            InputFilter[] newFilters = new InputFilter[oldFilters.length + 1];
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
            newFilters[oldFilters.length] = getBlankChineseInputFilter(allowBlank, allowChinese);
            editText.setFilters(newFilters);

        }

        editText.setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        editText.setLongClickable(false);
    }

    private static class ActionModeCallbackInterceptor implements ActionMode.Callback {


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }


    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = false;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (isChinese(cTemp[i])) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 中文两个长度，其它一个长度，按长度截取内容，后面加“...”
     *
     * @param content
     * @param srcLen
     * @return
     */
    public static String getFilterContent(String content, int srcLen) {
        try {
            if (TextUtils.isEmpty(content)) {
                return "";
            }

            int count = 0;
            for (int i = 0; i < content.length(); i++) {
                String word = String.valueOf(content.charAt(i));
                if (RegexUtils.hadCn(word)) {
                    count += 2;
                } else {
                    count += 1;
                }

                if (count >= srcLen) {
                    String preResult = content.substring(0, i + 1);
                    //还要判断是否和原字符串一样，才决定加...
                    String result = preResult.equals(content) ? preResult : preResult + "...";
                    return result;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }


}
