/*
 * Copyright (c) 2021 Video
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.videogamesm12.multihotbar.events;

import me.videogamesm12.multihotbar.callbacks.HotbarLoadFailCallback;
import me.videogamesm12.multihotbar.callbacks.HotbarSaveFailCallback;
import me.videogamesm12.multihotbar.util.HotbarToast;
import me.videogamesm12.multihotbar.util.Util;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;

/**
 * HotbarFailListener - Listens for HotbarLoadFailCallbacks and HotbarSaveFailCallbacks to display error messages when
 * hotbar files fail to save or load.
 * @author Video
 */
public class HotbarFailListener implements HotbarLoadFailCallback, HotbarSaveFailCallback
{
    @Override
    public ActionResult onHotbarLoadFail()
    {
        Util.showToastMessage(HotbarToast.Type.FAILED, new TranslatableText("toast.failed.load"), new LiteralText(Util.getHotbarName()), false);
        Util.showOverlayMessage(new TranslatableText("overlay.hotbar_load_failed", Util.getHotbarName()));
        //
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult onHotbarSaveFail()
    {
        Util.showToastMessage(HotbarToast.Type.FAILED, new TranslatableText("toast.failed.save"), new LiteralText(Util.getHotbarName()), false);
        Util.showOverlayMessage(new TranslatableText("overlay.hotbar_save_failed", Util.getHotbarName()));
        //
        return ActionResult.SUCCESS;
    }
}
