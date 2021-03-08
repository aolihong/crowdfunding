package com.aolhon.crowd;

import org.junit.Test;

import com.aolhon.crowd.util.CrowdUtil;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021Âπ?1Êú?31Êó? ‰∏äÂçà12:31:53
 * 
 */
public class StringEncodeTest {
	@Test
	public void testMd5(){
		String source = "123";
		String md5 = CrowdUtil.md5(source);
		System.out.println(md5);
	}
}
