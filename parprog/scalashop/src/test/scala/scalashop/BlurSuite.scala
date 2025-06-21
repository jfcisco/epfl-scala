package scalashop

import java.util.concurrent.*
import scala.collection.*

class BlurSuite extends munit.FunSuite:
  trait TestImages {
    val img: Img = {
      val init = Img(3, 4)
      init(0, 0) = rgba(179, 51, 128, 255)
      init(0, 1) = rgba(77, 204, 26, 153)
      init(0, 2) = rgba(230, 102, 153, 204)
      init(0, 3) = rgba(51, 153, 77, 230)
      init(1, 0) = rgba(128, 26, 179, 102)
      init(1, 1) = rgba(153, 77, 51, 179)
      init(1, 2) = rgba(102, 204, 128, 77)
      init(1, 3) = rgba(204, 51, 153, 128)
      init(2, 0) = rgba(77, 153, 102, 204)
      init(2, 1) = rgba(179, 128, 26, 153)
      init(2, 2) = rgba(51, 230, 77, 102)
      init(2, 3) = rgba(153, 102, 179, 51)
      init
    }
  }

  // Put tests here
  test("boxBlurKernel") {
    new TestImages:
      val result = boxBlurKernel(img, 1, 1, 1)
      val expected = rgba(130, 130, 96, 158)
      assertEquals(result, expected)
  }

  test("boxBlurKernel near boundary") {
    new TestImages:
      val result = boxBlurKernel(img, 2, 3, 1)
      assertEquals(result, rgba(127, 146, 134, 89))
  }

  test("boxBlurKernel with radius 0") {
    new TestImages:
      val result = boxBlurKernel(img, 1, 1, 0)
      assertEquals(result, rgba(153, 77, 51, 179))
  }

  test("HorizontalBoxBlur.parBlur should not throw an exception when too many tasks are available") {
    new TestImages:
      HorizontalBoxBlur.parBlur(img, Img(3, 4), 5, 1)
  }

  test("VerticalBoxBlur.parBlur should not throw an exception when too many tasks are available") {
    new TestImages:
      VerticalBoxBlur.parBlur(img, Img(3, 4), 5, 1)
  }
