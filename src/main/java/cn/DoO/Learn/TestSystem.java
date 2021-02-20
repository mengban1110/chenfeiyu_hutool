package cn.DoO.Learn;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;

/**
 * 系统相关属性
 * @author 梦伴
 *
 */
public class TestSystem {
	
	
	/**
	 * junit测试入口函数
	 */
	@Test
	@Comment("系统属性工具")
	public void test1() {
		p5("java虚拟机规范", StrUtil.trim(SystemUtil.getJvmSpecInfo().toString()));
		p5("当前虚拟机信息", StrUtil.trim(SystemUtil.getJvmInfo().toString()));
		p5("java规范", StrUtil.trim(SystemUtil.getJavaSpecInfo().toString()));
		p5("当前java信息", StrUtil.trim(SystemUtil.getJavaInfo().toString()));
		p5("java运行时信息", StrUtil.trim(SystemUtil.getJavaRuntimeInfo().toString()));
		p5("操作系统信息", StrUtil.trim(SystemUtil.getOsInfo().toString()));
		p5("用户信息", StrUtil.trim(SystemUtil.getUserInfo().toString()));
		p5("主机信息", StrUtil.trim(SystemUtil.getHostInfo().toString()));
		p5("内存信息", StrUtil.trim(SystemUtil.getRuntimeInfo().toString()));
	}

	private String preComment = null;

	
	
	
	/**
	 * 为测试输出 写好准备方法 
	 */
	private void c(String msg) {
		System.out.printf("\t备注：%s%n", msg);
	}

	private void p1(String type1, Object value1, String type2, Object value2) {
		p(type1, value1, type2, value2, "format1");
	}

	private void p2(String type1, Object value1, String type2, Object value2) {
		p(type1, value1, type2, value2, "format2");
	}

	private void p3(String type1, Object value1) {
		p(type1, value1, "", "", "format3");
	}

	private void p5(String type1, Object value1) {
		p(type1, value1, "", "", "format5");
	}

	private void p4(Object value) {
		p(null, value, "", "", "format4");
	}

	/**
	 * 调用主题函数 依次输出对应信息
	 * @param type1
	 * @param value1
	 * @param type2
	 * @param value2
	 * @param format
	 */
	private void p(String type1, Object value1, String type2, Object value2, String format) {
		try {
			throw new Exception();
		} catch (Exception e) {

			String methodName = getTestMethodName(e.getStackTrace());//获取方法名字
			Method m = ReflectUtil.getMethod(this.getClass(), methodName);//通过反射的形式获取函数Call
			/**
			 * 调用
			 */
			Comment annotation = m.getAnnotation(Comment.class);
			if (null != annotation) {
				String comment = annotation.value();
				if (!comment.equals(preComment)) {
					System.out.printf("%n%s 例子： %n%n", comment);
					preComment = comment;
				}

			}
		}
		
		/**
		 * 判断格式化的形式
		 */
		int padLength = 12;
		type1 = StrUtil.padEnd(type1, padLength, Convert.toSBC(" ").charAt(0));
		type2 = StrUtil.padEnd(type2, padLength, Convert.toSBC(" ").charAt(0));
		if ("format1".equals(format)) {
			System.out.printf("\t%s的:\t\"%s\" %n\t被转换为----->%n\t%s的 :\t\"%s\" %n%n", type1, value1, type2, value2);
		}
		if ("format2".equals(format)) {
			System.out.printf("\t基于 %s:\t\"%s\" %n\t获取 %s:\t\"%s\"%n%n", type1, value1, type2, value2);
		}
		if ("format3".equals(format)) {
			System.out.printf("\t%s:\t\"%s\" %n\t%n", type1, value1);

		}
		if ("format4".equals(format)) {
			System.out.printf("\t%s%n%n", value1);
		}
		if ("format5".equals(format)) {
			System.out.printf("---------%s-------:%n%s %n%n", type1, value1);
		}
	}

	/**
	 * 获取方法名字
	 * @param stackTrace
	 * @return
	 */
	private String getTestMethodName(StackTraceElement[] stackTrace) {
		for (StackTraceElement se : stackTrace) {
			String methodName = se.getMethodName();
			if (methodName.startsWith("test"))
				return methodName;
		}
		return null;
	}

	@Target({ ElementType.METHOD, ElementType.TYPE })//设定注入目标
	@Retention(RetentionPolicy.RUNTIME)//注解的生命周期
	public @interface Comment {
		String value();
	}

}
