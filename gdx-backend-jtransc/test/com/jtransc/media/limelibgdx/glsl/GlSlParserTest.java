package com.jtransc.media.limelibgdx.glsl;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class GlSlParserTest {
	@Test
	public void testPreprocessor() throws Exception {
		String shader = ("" +
			"#ifdef GL_ES\n" +
			"#define LOWP lowp\n" +
			"precision mediump float;\n" +
			"#else\n" +
			"#define LOWP\n" +
			"#endif\n" +
			"varying LOWP vec4 v_color;\n" +
			"varying vec2 v_texCoords;\n" +
			"uniform sampler2D u_texture;\n" +
			"void main()\n" +
			"{\n" +
			"	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" +
			"}\n"
		);

		Assert.assertEquals(
			String.join("\n", new String[]{
				"precision mediump float;",
				"varying lowp vec4 v_color;",
				"varying vec2 v_texCoords;",
				"uniform sampler2D u_texture;",
				"void main()",
				"{",
				"	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);",
				"}"
			})
			,
			GlSlParser.preprocess(shader, new HashMap<String, String>() {{
				put("GL_ES", "1");
			}})
		);

		Assert.assertEquals(
			String.join("\n", new String[]{
				"varying  vec4 v_color;",
				"varying vec2 v_texCoords;",
				"uniform sampler2D u_texture;",
				"void main()",
				"{",
				"	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);",
				"}"
			})
			,
			GlSlParser.preprocess(shader, new HashMap<String, String>() {{
			}})
		);
	}
}
