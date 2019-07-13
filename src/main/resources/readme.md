
        //1.创建QueryBuilder(即设置查询条件)这儿创建的是组合查询(也叫多条件查询),后面会介绍更多的查询方法
        /*组合查询BoolQueryBuilder
         * must(QueryBuilders)   :AND
         * mustNot(QueryBuilders):NOT
         * should:               :OR
         */
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //builder下有must、should以及mustNot 相当于sql中的and、or以及not

        //设置模糊搜索,博客的简诉中有学习两个字
        builder.must(QueryBuilders.fuzzyQuery("sumary", "学习"));

        //设置要查询博客的标题中含有关键字
        builder.must(new QueryStringQueryBuilder("man").field("springdemo"));

        //按照博客的评论数的排序是依次降低
        FieldSortBuilder sort = SortBuilders.fieldSort("commentSize").order(SortOrder.DESC);

        //设置分页(从第一页开始，一页显示10条)
        //注意开始是从0开始，有点类似sql中的方法limit 的查询
        PageRequest page = new PageRequest(0, 10);

        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将搜索条件设置到构建中
        nativeSearchQueryBuilder.withQuery(builder);
        //将分页设置到构建中
        nativeSearchQueryBuilder.withPageable(page);
        //将排序设置到构建中
        nativeSearchQueryBuilder.withSort(sort);
        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();

        //3.执行方法1
//        Page<EsBlog> page = esBlogRepository.search(query);
//    }
//4.2查询条件QueryBuilder的构建方法举例
//            在使用聚合查询之前我们有必要先来了解下创建查询条件QueryBuilder的几种常用方法
//
//4.2.1精确查询（必须完全匹配上）
//    单个匹配termQuery

    //不分词查询 参数1： 字段名，参数2：字段查询值，因为不分词，所以汉字只能查询一个字，英语是一个单词.
    QueryBuilder queryBuilder=QueryBuilders.termQuery("fieldName", "fieldlValue");
    //分词查询，采用默认的分词器
    QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("fieldName", "fieldlValue");