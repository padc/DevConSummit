/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.devcon.android.news.event;

import java.util.List;

import ph.devcon.android.news.db.News;

/**
 * Created by lope on 10/29/14.
 */
public class FetchedNewsListEvent {
    public List<News> newsList;

    public FetchedNewsListEvent(List<News> newsList) {
        this.newsList = newsList;
    }
}
