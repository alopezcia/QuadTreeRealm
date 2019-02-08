// Please note : [Backlink] properties and default values are not represented
// in the schema and thus will not be part of the generated models

using System;
using System.Collections.Generic;
using Realms;

namespace MyProject.Models
{
    public class QuadTreeNode : RealmObject
    {
        [PrimaryKey]
        [MapTo("id")]
        public long Id { get; set; }

        [MapTo("extent")]
        public QuadTreeExtent Extent { get; set; }

        [MapTo("nodes")]
        public IList<QuadTreeNode> Nodes { get; }

        [MapTo("entitites")]
        public IList<QuadTreeEntity> Entitites { get; }
    }
}
