// Please note : [Backlink] properties and default values are not represented
// in the schema and thus will not be part of the generated models

using System;
using System.Collections.Generic;
using Realms;

namespace MyProject.Models
{
    public class QuadTreeExtent : RealmObject
    {
        [PrimaryKey]
        [MapTo("id")]
        public long Id { get; set; }

        [MapTo("minLat")]
        public double MinLat { get; set; }

        [MapTo("minLng")]
        public double MinLng { get; set; }

        [MapTo("maxLat")]
        public double MaxLat { get; set; }

        [MapTo("maxLng")]
        public double MaxLng { get; set; }
    }
}
