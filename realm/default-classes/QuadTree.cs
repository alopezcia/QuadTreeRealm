// Please note : [Backlink] properties and default values are not represented
// in the schema and thus will not be part of the generated models

using System;
using System.Collections.Generic;
using Realms;

namespace MyProject.Models
{
    public class QuadTree : RealmObject
    {
        [PrimaryKey]
        [MapTo("id")]
        public long Id { get; set; }

        [Required]
        [MapTo("layerName")]
        public string LayerName { get; set; }

        [MapTo("featCount")]
        public long FeatCount { get; set; }

        [MapTo("rootNode")]
        public QuadTreeNode RootNode { get; set; }
    }
}
