/*
 * Copyright (c) 2023 Video
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

package me.videogamesm12.hotbarsplus.api.provider;

import me.videogamesm12.hotbarsplus.core.universal.NotificationManager;

import java.util.List;

/**
 * <h1>INotificationRouteProvider</h1>
 * <p>Hotbars+'s new way of registering notification routes as of v2.0-pre10.</p>
 * <p>To use this, implement this interface in your project as a provider and have it return a list of route classes. Then, add a path to the class in your fabric.mod.json file as an entrypoint for "hotbarsplus".</p>
 */
public interface INotificationRouteProvider
{
    List<Class<? extends NotificationManager.NotificationRoute>> getNotificationRoutes();
}
